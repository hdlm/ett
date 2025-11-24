package com.budoxr.ett.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerTrackingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timeTracking: TimerTrackingEntity)

    @Delete
    suspend fun delete(timeTracking: TimerTrackingEntity)

    @Query("SELECT * FROM timer_tracking_activities WHERE timer_tracking_id = :id")
    suspend fun getById(id: Long): TimerTrackingEntity?

    @Query("SELECT * FROM timer_tracking_activities WHERE timer_tracking_id = :id")
    fun observeById(id: Long): Flow<TimerTrackingEntity?>

    @Query("SELECT * FROM timer_tracking_activities")
    suspend fun getTimersWithActivities(): List<TimersWithActivity>

    @Query("SELECT * FROM timer_tracking_activities")
    fun observeTimersWithActivities(): Flow<List<TimersWithActivity>>

    @Query("SELECT * FROM timer_tracking_activities WHERE done = 1")
    suspend fun getActiveTimersWithActivities(): List<TimersWithActivity>

    @Query("SELECT * FROM timer_tracking_activities WHERE done = 1")
    fun observeActiveTimersWithActivities(): Flow<List<TimersWithActivity>>


}