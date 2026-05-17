/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.budoxr.ett.data.database.entities.TimerTrackingEntity

data class TimerTrackingQuery(
    @Embedded
    val timerTracking: TimerTrackingEntity,
    @ColumnInfo(name = "name_activity") val nameActivity: String
)
