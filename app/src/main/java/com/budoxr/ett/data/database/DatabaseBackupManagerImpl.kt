/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import android.content.Context
import android.os.Environment
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
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
    private val databaseName: String
) : DatabaseBackupManager, KoinComponent {

    /**
     * Safely backs up the Room SQLite database file to a specified destination file.
     * This function is main-safe and executes entirely on Dispatchers.IO.
     */
    override suspend fun backupDatabase(destinationFile: File): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("backupDatabase() -> Destination: ${destinationFile.absolutePath}")

            // 1. Force a checkpoint to merge WAL journal files into the main .db file
            roomDatabase.query(SimpleSQLiteQuery("PRAGMA wal_checkpoint(FULL)"))

            // 2. Locate the source database file paths
            val dbFile: File = context.getDatabasePath(databaseName)
            if (!dbFile.exists()) {
                Timber.tag(TAG).e("backupDatabase() -> Source database file not found at: ${dbFile.absolutePath}")
                return@withContext Result.failure(FileNotFoundException("Source database file not found."))
            } else {
                // 3. Perform a highly efficient streaming block copy
                dbFile.inputStream().use { input ->
                    FileOutputStream(destinationFile).use { output ->
                        input.copyTo(output)
                    }
                }
                Timber.tag(TAG).i("backupDatabase() -> Success! File saved at: ${destinationFile.absolutePath}")
                Result.success(Unit)
            }
        } catch (e: IOException) {
            Timber.tag(TAG).e(e, "backupDatabase() -> IOException")
            Result.failure(e)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "backupDatabase() -> Unexpected error")
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
            roomDatabase.close()

            // 2. Locate the target database file path
            val dbFile: File = context.getDatabasePath(databaseName)

            // 3. Delete existing database files (including WAL and SHM) to ensure a clean restore
            if (dbFile.exists()) {
                // context.deleteDatabase is the safest way to remove a Room database and its support files
                context.deleteDatabase(databaseName)
            }

            // 4. Perform the copy from source to the database location
            sourceFile.inputStream().use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }

            Timber.tag(TAG).i("restoreDatabase() -> Success! Database restored from: ${sourceFile.absolutePath}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "restoreDatabase() -> Unexpected error")
            Result.failure(e)
        }
    }

    /**
     * Returns the reference to the app-specific Download folder in the internal local storage.
     * Path example: /storage/emulated/0/Android/data/com.budoxr.ett/files/Download
     */
    override fun getDownloadFolder(): File? {
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        Timber.tag(TAG).d("getDownloadFolder() -> Returning: ${folder?.absolutePath}")
        return folder
    }

    companion object {
        private const val TAG = "che.DatabaseBackupManager"
    }
}
