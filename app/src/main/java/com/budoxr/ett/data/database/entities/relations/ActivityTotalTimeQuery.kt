/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.budoxr.ett.data.database.entities.ActivityEntity

data class ActivityTotalTimeQuery(
    @Embedded
    val activity: ActivityEntity,
    @ColumnInfo(name = "total_elapsed_time") val totalElapsedTime: Long
)
