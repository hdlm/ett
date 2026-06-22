/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
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
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun timeTrackingDao(): TimerTrackingDao
    abstract fun groupActivitiesDao(): GroupActivityDao
}
