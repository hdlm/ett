/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow

interface TimerTrackingLocalRepository {
    suspend fun insert(timeTracking: TimerTrackingEntity) : Long
    suspend fun delete(timeTracking: TimerTrackingEntity)
    suspend fun getById(id: Long): TimerTrackingEntity?
    fun observeById(id: Long): Flow<TimerTrackingEntity?>
    suspend fun getAllTimers(): List<TimersWithActivity>
    fun observeAllTimers(): Flow<List<TimersWithActivity>>

    suspend fun getAllVisibleTimers(): List<TimersWithActivity>
    fun observeAllVisibleTimers(): Flow<List<TimersWithActivity>>

    fun observeByDateRangeTimers(sundayDate: String, saturdayDate: String): Flow<List<TimersWithActivity>>
    fun observeActivityTotalTimeQuery(startDate: String, endDate: String): Flow<List<ActivityTotalTimeQuery>>

    fun observeAllTimersTrackingQuery() : Flow<List<TimerTrackingQuery>>
}