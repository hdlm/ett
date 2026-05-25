/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.ActivityDao
import com.budoxr.ett.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ActivityLocalRepositoryImpl : ActivityLocalRepository, KoinComponent {
    private val activityDao by inject<ActivityDao>()

    override suspend fun insert(activity: ActivityEntity): Long =
        activityDao.insert(activity)

    override suspend fun update(activity: ActivityEntity) {
        activityDao.update(activity)
    }

    override suspend fun delete(activity: ActivityEntity) {
        activityDao.delete(activity)
    }

    override suspend fun getById(id: Long): ActivityEntity? =
        activityDao.getById(id)

    override suspend fun getByName(name: String): ActivityEntity? =
        activityDao.getByName(name)

    override fun observeById(id: Long): Flow<ActivityEntity?> =
        activityDao.observeById(id)

    override suspend fun getAll(): List<ActivityEntity> =
        activityDao.getAll()

    override fun observeAll(): Flow<List<ActivityEntity>> =
        activityDao.observeAll()

}
