package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.ActivityDao
import com.budoxr.ett.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ActivityLocalRepositoryImpl : ActivityLocalRepository, KoinComponent {
    private val activityDao by inject<ActivityDao>()

    override suspend fun insert(activity: ActivityEntity) {
        activityDao.insert(activity)
    }

    override suspend fun delete(activity: ActivityEntity) {
        activityDao.delete(activity)
    }

    override suspend fun getById(id: Int): ActivityEntity? =
        activityDao.getById(id)

    override fun observeById(id: Int): Flow<ActivityEntity?> =
        activityDao.observeById(id)

    override suspend fun getAll(): List<ActivityEntity> =
        activityDao.getAll()

    override fun observeAll(): Flow<List<ActivityEntity>> =
        activityDao.observeAll()

}