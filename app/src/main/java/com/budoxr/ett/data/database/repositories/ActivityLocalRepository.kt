package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

interface ActivityLocalRepository {
    suspend fun insert(activity: ActivityEntity)
    suspend fun delete(activity: ActivityEntity)
    suspend fun getById(id: Int): ActivityEntity?
    fun observeById(id: Int): Flow<ActivityEntity?>
    suspend fun getAll(): List<ActivityEntity>
    fun observeAll(): Flow<List<ActivityEntity>>
}
