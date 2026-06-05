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
import com.budoxr.ett.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(activity: ActivityEntity): Long

    @Update
    suspend fun update(activity: ActivityEntity)

    @Delete
    suspend fun delete(activity: ActivityEntity)

    @Query("SELECT * FROM activities WHERE activity_id = :id")
    suspend fun getById(id: Long): ActivityEntity?

    @Query("SELECT * FROM activities WHERE activity_id = :id")
    fun observeById(id: Long): Flow<ActivityEntity?>

    @Query("SELECT * FROM activities WHERE name = :name")
    suspend fun getByName(name: String): ActivityEntity?

    @Query("SELECT * FROM activities")
    suspend fun getAll(): List<ActivityEntity>

    @Query("SELECT * FROM activities")
    fun observeAll(): Flow<List<ActivityEntity>>

}
