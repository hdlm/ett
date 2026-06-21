/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivityWithTimers(
    @Embedded
    @property:Json(name = "activity")
    val activity: ActivityEntity,
    @Relation(
        parentColumn = "activity_id",
        entityColumn = "activity_id"
    )
    @property:Json(name = "timers")
    val timers: List<TimerTrackingEntity>
)
