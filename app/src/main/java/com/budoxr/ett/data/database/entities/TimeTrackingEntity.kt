package com.budoxr.ett.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "time_tracking_activities",
    foreignKeys = [
        ForeignKey(
            entity = ActivityEntity::class,
            parentColumns = ["activity_id"],
            childColumns = ["activity_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index("time_tracking_id", unique = true),
        Index("activity_id")
    ]
)
data class TimeTrackingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "time_tracking_id") val timeTrackingId: Long? = null,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String? = null,
    @ColumnInfo(name = "elapsed_time") val elapsedTime: Long = 0,
    /** foreign key */
    @ColumnInfo(name = "activity_id") val activityId: Int,

)
