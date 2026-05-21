/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import java.io.File

interface DatabaseBackupManager {
    /**
     * Safely backs up the Room SQLite database file to a specified destination file.
     */
    suspend fun backupDatabase(destinationFile: File): Result<Unit>

    /**
     * Restores the database from a source file.
     */
    suspend fun restoreDatabase(sourceFile: File): Result<Unit>

    /**
     * Returns the reference to the app-specific Download folder in the internal local storage.
     */
    fun getDownloadFolder(): File?
}
