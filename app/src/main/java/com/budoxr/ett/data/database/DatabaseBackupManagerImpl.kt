/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import android.content.Context
import android.os.Environment
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class DatabaseBackupManagerImpl(
    private val context: Context,
    private val roomDatabase: RoomDatabase,
    private val databaseName: String
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
            roomDatabase.close()

            // 2. Locate the target database file path
            val dbFile: File = context.getDatabasePath(databaseName)

            // 3. Delete existing database files (including WAL and SHM) to ensure a clean restore
            // context.deleteDatabase is the safest way to remove a Room database and its support files
            context.deleteDatabase(databaseName)
            
            // Ensure the directory exists for restoration
            dbFile.parentFile?.mkdirs()

            // 4. Perform the copy from source to the database location
            sourceFile.inputStream().use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }

            Timber.tag(TAG).i("restoreDatabase() -> Success! Database restored to: ${sourceFile.absolutePath}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "restoreDatabase() -> Unexpected error during restore")
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
