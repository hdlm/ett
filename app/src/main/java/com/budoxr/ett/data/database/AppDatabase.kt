package com.budoxr.ett.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budoxr.ett.data.database.daos.ActivityDao
import com.budoxr.ett.data.database.daos.TimeTrackingDao
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimeTrackingEntity

@Database(
    entities = [
        ActivityEntity::class,
        TimeTrackingEntity::class
    ],
    version = 1, exportSchema = false )
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun timeTrackingDao(): TimeTrackingDao
}