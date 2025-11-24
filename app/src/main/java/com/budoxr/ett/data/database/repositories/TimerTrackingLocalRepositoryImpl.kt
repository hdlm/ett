package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.TimerTrackingDao
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TimerTrackingLocalRepositoryImpl : TimerTrackingLocalRepository, KoinComponent {
    private val timerTrackingDao by inject<TimerTrackingDao>()

    override suspend fun insert(timeTracking: TimerTrackingEntity) {
        timerTrackingDao.insert(timeTracking)
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

    override suspend fun getAllActiveTimers(): List<TimersWithActivity> =
        timerTrackingDao.getActiveTimersWithActivities()

    override fun observeAllActiveTimers(): Flow<List<TimersWithActivity>> =
        timerTrackingDao.observeActiveTimersWithActivities()


}