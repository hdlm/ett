package com.budoxr.ett.data.database.repositories

import com.budoxr.ett.data.database.daos.GroupActivityDao
import com.budoxr.ett.data.database.entities.GroupActivityEntity
import kotlinx.coroutines.flow.Flow

class GroupActivityRepositoryImpl(
    private val groupActivityDao : GroupActivityDao
) : GroupActivityLocalRepository {

    override suspend fun insert(groupActivity: GroupActivityEntity): Long =
        groupActivityDao.insert(groupActivity)

    override suspend fun update(groupActivity: GroupActivityEntity) =
        groupActivityDao.update(groupActivity)

    override suspend fun delete(groupActivity: GroupActivityEntity) =
        groupActivityDao.delete(groupActivity)

    override suspend fun getById(id: Long): GroupActivityEntity? =
        groupActivityDao.getById(id)

    override suspend fun getByName(name: String): GroupActivityEntity? =
        groupActivityDao.getByName(name)

    override fun observeById(id: Long): Flow<GroupActivityEntity?> =
        groupActivityDao.observeById(id)

    override suspend fun getAll(): List<GroupActivityEntity> =
        groupActivityDao.getAll()

    override fun observeAll(): Flow<List<GroupActivityEntity>> =
        groupActivityDao.observeAll()

}