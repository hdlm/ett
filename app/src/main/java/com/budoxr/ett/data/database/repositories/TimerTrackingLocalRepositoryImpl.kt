/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.TimerTrackingDao
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TimerTrackingLocalRepositoryImpl : TimerTrackingLocalRepository, KoinComponent {
    private val timerTrackingDao by inject<TimerTrackingDao>()

    override suspend fun insert(timeTracking: TimerTrackingEntity) : Long {
        return timerTrackingDao.insert(timeTracking)
    }

    override suspend fun delete(timeTracking: TimerTrackingEntity) {
        timerTrackingDao.delete(timeTracking)
    }

    override suspend fun getById(id: Long): TimerTrackingEntity? =
        timerTrackingDao.getById(id)

    override fun observeById(id: Long): Flow<TimerTrackingEntity?> =
        timerTrackingDao.observeById(id)

    override suspend fun getAllTimers(): List<TimersWithActivity> =
        timerTrackingDao.getTimersWithActivities()

    override fun observeAllTimers(): Flow<List<TimersWithActivity>> =
        timerTrackingDao.observeTimersWithActivities()

    override suspend fun getAllVisibleTimers(): List<TimersWithActivity> =
        timerTrackingDao.getVisibleTimersWithActivities()

    override fun observeAllVisibleTimers(): Flow<List<TimersWithActivity>> =
        timerTrackingDao.observeVisibleTimersWithActivities()

    override fun observeByDateRangeTimers(sundayDate: String, saturdayDate: String): Flow<List<TimersWithActivity>> =
        timerTrackingDao.observeTimersByDateRangeWithActivities(sundayDate, saturdayDate)

    override fun observeActivityTotalTimeQuery(startDate: String, endDate: String): Flow<List<ActivityTotalTimeQuery>> =
        timerTrackingDao.observeActivitiesTotalTime(startDate, endDate)

    override fun observeAllTimersTrackingQuery() : Flow<List<TimerTrackingQuery>> =
        timerTrackingDao.observeAllTimersTracking()


}