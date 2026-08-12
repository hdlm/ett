/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import android.content.Context
import android.net.Uri
import androidx.room.RoomDatabase
import com.budoxr.ett.commons.utils.FileUtils
import com.budoxr.ett.commons.utils.TimeUtils.toTimestampFormat
import com.budoxr.ett.data.database.entities.relations.ActivityWithTimers
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class DatabaseBackupManagerImpl(
    private val context: Context,
    private val roomDatabase: RoomDatabase,
    private val databaseName: String,
    private val fileUtils: FileUtils,
    private val moshi: Moshi
) : DatabaseBackupManager, KoinComponent {

    private val activitiesAdapter by lazy {
        val type = Types.newParameterizedType(List::class.java, ActivityWithTimers::class.java)
        moshi.adapter<List<ActivityWithTimers>>(type)
    }

    /**
     * Safely backs up the Room SQLite database file to a specified destination file.
     *
     * Optimized for SDK 29+: Uses 'VACUUM INTO' for an atomic and consistent single-file backup.
     * This command handles WAL mode consistency automatically by merging the log into the destination.
     */
    override suspend fun backupDatabase(destinationFile: File): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("backupDatabase() -> Destination: ${destinationFile.absolutePath}")

            // 1. Prepare destination: ensure parent directory exists
            destinationFile.parentFile?.mkdirs()

            // 2. VACUUM INTO requires the target file to NOT exist
            if (destinationFile.exists()) {
                destinationFile.delete()
            }

            // 3. Execute VACUUM INTO via the low-level database handle
            val db = roomDatabase.openHelper.writableDatabase
            db.execSQL("VACUUM INTO '${destinationFile.absolutePath}'")

            Timber.tag(TAG).i("backupDatabase() -> Success using VACUUM INTO at: ${destinationFile.absolutePath}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "backupDatabase() -> Error during backup")
            Result.failure(e)
        }
    }

    /**
     * Restores the database from a source file.
     * Closes the database connection, replaces the file, and returns success if completed.
     */
    override suspend fun restoreDatabase(sourceFile: File): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("restoreDatabase() -> Source: ${sourceFile.absolutePath}")
            if (!sourceFile.exists()) {
                Timber.tag(TAG).e("restoreDatabase() -> Source file not found at: ${sourceFile.absolutePath}")
                return@withContext Result.failure(FileNotFoundException("Source backup file not found: ${sourceFile.absolutePath}"))
            }

            // 1. Close the database to stop all operations and release file locks
            Timber.tag(TAG).d("restoreDatabase() -> Closing active database connection.")
            roomDatabase.close()

            // 2. Locate the target database file path
            val dbFile: File = context.getDatabasePath(databaseName)

            // 3. Delete existing database files (including WAL and SHM) to ensure a clean restore
            Timber.tag(TAG).d("restoreDatabase() -> Deleting current database files.")
            context.deleteDatabase(databaseName)

            // Ensure the directory exists for restoration
            dbFile.parentFile?.mkdirs()

            // 4. Perform the copy from source to the database location
            Timber.tag(TAG).d("restoreDatabase() -> Copying data from backup...")
            sourceFile.inputStream().use { input ->
                FileOutputStream(dbFile).use { outputStream ->
                    input.copyTo(outputStream)
                }
            }

            Timber.tag(TAG).i("restoreDatabase() -> Success! Database restored to: ${sourceFile.absolutePath}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "restoreDatabase() -> Error during restore")
            Result.failure(e)
        }
    }

    /**
     * Imports data from a CSV file received via URI by copying it to the cache directory.
     * The file is saved as 'data.csv' in the internal cache.
     */
    override suspend fun importFromCsv(uri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("importFromCsv() -> URI: $uri")

            val targetFile = fileUtils.copyUriToCache(uri, CSV_FILE)
                ?: throw FileNotFoundException("Could not copy URI: $uri to cache.")

            val data = fileUtils.readCsvContent(csvFile = targetFile)

            Timber.tag(TAG).i("importFromCsv() -> Success! File copied to cache as $CSV_FILE")
            Result.success(data)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "importFromCsv() -> Error during CSV import copy")
            Result.failure(e)
        }
    }

    override suspend fun exportToJson(activities: Set<ActivityWithTimers>): Result<Unit> {
        Timber.tag(TAG).d("exportToJson() called.")

        return withContext(Dispatchers.Default) {
            try {
                // Optional: Use indent("  ") if you need a pretty-printed, readable JSON structure
                val jsonString = activitiesAdapter.indent("  ").toJson(activities.toList())
                fileUtils.saveJsonToPublicDownloads(JSON_FILE, jsonString)
                Result.success(Unit)

            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }

    override suspend fun exportToCsv(activities: Set<ActivityWithTimers>): Result<Unit> {
        Timber.tag(TAG).d("exportToCsv() called.")
        val totalElapsedTimeByActivity: MutableList<Pair<String, Long>> = mutableListOf()
        activities.forEach { item ->
            val totalElapsedTime = item.timers.sumOf { it.elapsedTime }
            val name = item.activity.name
            totalElapsedTimeByActivity.add(Pair(name, totalElapsedTime))
        }
        return withContext(Dispatchers.Default) {
            try {
                fileUtils.saveCsvToPublicDownloads(CSV_FILE, totalElapsedTimeByActivity)
                Result.success(Unit)

            } catch (e: IOException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }


    override suspend fun importFromJson(uri: Uri): Result<List<ActivityWithTimers>> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("importFromJson() -> URI: $uri")

            val targetFle = fileUtils.copyUriToCache(uri, JSON_FILE)
                ?: throw FileNotFoundException("Could not copy URI: $uri to cache.")

            val jsonString = fileUtils.readJsonContent(jsonFile = targetFle)
            val data = activitiesAdapter.fromJson(jsonString) ?: throw IOException("Failed to parse JSON")
            Result.success(data)

        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "importFromJson() -> Error during import")
            Result.failure(e)
        }


    }


    companion object {
        private const val TAG = "che.DatabaseBackupManager"
        const val CSV_FILE = "ett_data.csv"
        const val JSON_FILE = "ett_data.json"
    }
}
