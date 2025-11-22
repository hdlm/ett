package com.budoxr.ett.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budoxr.ett.data.database.entities.TimeTrackingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeTrackingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timeTracking: TimeTrackingEntity)

    @Delete
    suspend fun delete(timeTracking: TimeTrackingEntity)

    @Query("SELECT * FROM time_tracking_activities WHERE time_tracking_id = :id")
    suspend fun getById(id: Long): TimeTrackingEntity?

    @Query("SELECT * FROM time_tracking_activities WHERE time_tracking_id = :id")
    fun observeById(id: Long): Flow<TimeTrackingEntity?>

    @Query("SELECT * FROM time_tracking_activities WHERE activity_id = :activityId")
    suspend fun getByAllByActivity(activityId: Int): List<TimeTrackingEntity>

    @Query("SELECT * FROM time_tracking_activities WHERE activity_id = :activityId")
    fun observeAllByActivity(activityId: Int): Flow<List<TimeTrackingEntity>>

}