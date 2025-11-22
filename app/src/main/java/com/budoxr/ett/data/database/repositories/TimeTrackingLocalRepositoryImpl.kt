package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.TimeTrackingDao
import com.budoxr.ett.data.database.entities.TimeTrackingEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TimeTrackingLocalRepositoryImpl : TimeTrackingLocalRepository, KoinComponent {
    private val timeTrackingDao by inject<TimeTrackingDao>()

    override suspend fun insert(timeTracking: TimeTrackingEntity) {
        timeTrackingDao.insert(timeTracking)
    }

    override suspend fun delete(timeTracking: TimeTrackingEntity) {
        timeTrackingDao.delete(timeTracking)
    }

    override suspend fun getById(id: Long): TimeTrackingEntity? =
        timeTrackingDao.getById(id)

    override fun observeById(id: Long): Flow<TimeTrackingEntity?> =
        timeTrackingDao.observeById(id)

    override suspend fun getAllByActivity(activityId: Int): List<TimeTrackingEntity> =
        timeTrackingDao.getByAllByActivity(activityId)

    override fun observeAllByActivity(activityId: Int): Flow<List<TimeTrackingEntity>> =
        timeTrackingDao.observeAllByActivity(activityId)

}