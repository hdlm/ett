/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import android.net.Uri
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.ActivityWithTimers
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
     * Imports data from a CSV file into the database.
     */
    suspend fun importFromCsv(uri: Uri): Result<String>

    /**
     * Exports data from the database to a JSON file.
     */
    suspend fun exportToJson(activities: Set<ActivityWithTimers>): Result<Unit>

    /**
     * Exports data from the database to a CSV file.
     */
    suspend fun exportToCsv(activities: Set<ActivityWithTimers>): Result<Unit>


    /**
     * Imports data from a JSON file into the database.
     */
    suspend fun importFromJson(uri: Uri): Result<List<ActivityWithTimers>>

}
