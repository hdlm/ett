package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow

interface TimerTrackingLocalRepository {
    suspend fun insert(timeTracking: TimerTrackingEntity)
    suspend fun delete(timeTracking: TimerTrackingEntity)
    suspend fun getById(id: Long): TimerTrackingEntity?
    fun observeById(id: Long): Flow<TimerTrackingEntity?>
    suspend fun getAllTimers(): List<TimersWithActivity>
    fun observeAllTimers(): Flow<List<TimersWithActivity>>

    suspend fun getAllActiveTimers(): List<TimersWithActivity>
    fun observeAllActiveTimers(): Flow<List<TimersWithActivity>>

}