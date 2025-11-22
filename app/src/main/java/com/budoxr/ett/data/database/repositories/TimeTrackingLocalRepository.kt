package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.entities.TimeTrackingEntity
import kotlinx.coroutines.flow.Flow

interface TimeTrackingLocalRepository {
    suspend fun insert(timeTracking: TimeTrackingEntity)
    suspend fun delete(timeTracking: TimeTrackingEntity)
    suspend fun getById(id: Long): TimeTrackingEntity?
    fun observeById(id: Long): Flow<TimeTrackingEntity?>
    suspend fun getAllByActivity(activityId: Int): List<TimeTrackingEntity>
    fun observeAllByActivity(activityId: Int): Flow<List<TimeTrackingEntity>>

}