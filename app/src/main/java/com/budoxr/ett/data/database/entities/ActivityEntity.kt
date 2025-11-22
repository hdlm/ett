package com.budoxr.ett.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "activities"
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id") val activityId: Int? = null,
    val name: String,
    val color: Long? = null,
)
