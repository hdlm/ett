/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.budoxr.ett.data.database.entities.GroupActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(groupActivity: GroupActivityEntity): Long

    @Update
    suspend fun update(groupActivity: GroupActivityEntity)

    @Delete
    suspend fun delete(groupActivity: GroupActivityEntity)

    @Query("SELECT * FROM group_activities WHERE group_activity_id = :id")
    suspend fun getById(id: Long): GroupActivityEntity?

    @Query("SELECT * FROM group_activities WHERE group_activity_id = :id")
    fun observeById(id: Long): Flow<GroupActivityEntity?>

    @Query("SELECT * FROM group_activities WHERE name = :name")
    suspend fun getByName(name: String): GroupActivityEntity?

    @Query("SELECT * FROM group_activities")
    suspend fun getAll(): List<GroupActivityEntity>

    @Query("SELECT * FROM group_activities")
    fun observeAll(): Flow<List<GroupActivityEntity>>

}