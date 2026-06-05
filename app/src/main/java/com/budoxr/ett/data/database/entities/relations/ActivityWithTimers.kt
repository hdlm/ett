/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity

data class TimersWithActivity(
    @Embedded
    val timerTracking: TimerTrackingEntity,
    @Relation(
        parentColumn = "activity_id",
        entityColumn = "activity_id"
    )
    val activity: ActivityEntity
)
