/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.compose.ui.graphics.Color

@Entity(
    tableName = "activities",
    indices = [Index(value = ["name"], unique = true)]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id") val activityId: Long? = null,
    val name: String,
    val color: Color? = null,
)
