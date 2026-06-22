/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.budoxr.ett.data.database.daos.ActivityDao
import com.budoxr.ett.data.database.daos.GroupActivityDao
import com.budoxr.ett.data.database.daos.TimerTrackingDao
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.GroupActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity

@Database(
    entities = [
        ActivityEntity::class,
        TimerTrackingEntity::class,
        GroupActivityEntity::class,
    ],
    version = 2, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun timeTrackingDao(): TimerTrackingDao
    abstract fun groupActivitiesDao(): GroupActivityDao

    companion object {
        // 2. Explicitly specify the SQL required to alter the schema from version 1 to 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `group_activities` (
                        `group_activity_id` INTEGER PRIMARY KEY AUTOINCREMENT, 
                        `name` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_group_activities_name` ON `group_activities` (`name`)")
                db.execSQL("ALTER TABLE `activities` ADD COLUMN `group_activities_id` INTEGER")
            }
        }
    }
}
