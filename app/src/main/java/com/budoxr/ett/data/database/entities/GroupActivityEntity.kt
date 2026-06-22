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
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(
    tableName = "group_activities",
    indices = [Index(value = ["name"], unique = true)]
)
@JsonClass(generateAdapter = true)
data class GroupActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @property:Json(name = "group_activity_id")
    @ColumnInfo(name = "group_activity_id") val groupActivityId: Long? = null,
    val name: String,
)
