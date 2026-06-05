/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import android.content.Context
import android.net.Uri
import androidx.room.RoomDatabase
import com.budoxr.ett.commons.utils.CsvEncoding
import com.budoxr.ett.commons.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

class DatabaseBackupManagerImpl(
    private val context: Context,
    private val roomDatabase: RoomDatabase,
    private val databaseName: String,
    private val fileUtils: FileUtils,
) : DatabaseBackupManager, KoinComponent {

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

            val targetFile = File(context.cacheDir, CSV_FILE)

            // Using openFileDescriptor as a workaround for some Samsung devices/MTP issues
            // where openInputStream might fail with SecurityException even if URI permissions were granted.
            context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                FileInputStream(pfd.fileDescriptor).use { inputStream ->
                    FileOutputStream(targetFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            } ?: throw FileNotFoundException("Could not open file descriptor for URI: $uri")

            val data =  fileUtils.readCsvContent(csvFile = targetFile)

            Timber.tag(TAG).i("importFromCsv() -> Success! File copied to cache as $CSV_FILE")
            Result.success(data)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "importFromCsv() -> Error during CSV import copy")
            Result.failure(e)
        }
    }







    companion object {
        private const val TAG = "che.DatabaseBackupManager"
        const val CSV_FILE = "data.csv"
    }
}
