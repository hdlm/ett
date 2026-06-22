package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.entities.GroupActivityEntity
import kotlinx.coroutines.flow.Flow

interface GroupActivityLocalRepository {
    suspend fun insert(groupActivity: GroupActivityEntity): Long
    suspend fun update(groupActivity: GroupActivityEntity)
    suspend fun delete(groupActivity: GroupActivityEntity)
    suspend fun getById(id: Long): GroupActivityEntity?
    suspend fun getByName(name: String): GroupActivityEntity?
    fun observeById(id: Long): Flow<GroupActivityEntity?>
    suspend fun getAll(): List<GroupActivityEntity>
    fun observeAll(): Flow<List<GroupActivityEntity>>
}