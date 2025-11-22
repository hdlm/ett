package com.budoxr.ett.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budoxr.ett.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity)

    @Delete
    suspend fun delete(activity: ActivityEntity)

    @Query("SELECT * FROM activities WHERE activity_id = :id")
    suspend fun getById(id: Int): ActivityEntity?

    @Query("SELECT * FROM activities WHERE activity_id = :id")
    fun observeById(id: Int): Flow<ActivityEntity?>

    @Query("SELECT * FROM activities")
    suspend fun getAll(): List<ActivityEntity>

    @Query("SELECT * FROM activities")
    fun observeAll(): Flow<List<ActivityEntity>>


}