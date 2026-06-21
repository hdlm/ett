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
import androidx.room.Transaction
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.entities.relations.ActivityWithTimers
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerTrackingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timeTracking: TimerTrackingEntity): Long

    @Delete
    suspend fun delete(timeTracking: TimerTrackingEntity)

    @Query("SELECT * FROM timer_tracking_activities WHERE timer_tracking_id = :id")
    suspend fun getById(id: Long): TimerTrackingEntity?

    @Query("SELECT * FROM timer_tracking_activities WHERE timer_tracking_id = :id")
    fun observeById(id: Long): Flow<TimerTrackingEntity?>

    @Transaction
    @Query("SELECT * FROM timer_tracking_activities")
    suspend fun getTimersWithActivities(): List<TimersWithActivity>

    @Transaction
    @Query("SELECT * FROM timer_tracking_activities")
    fun observeTimersWithActivities(): Flow<List<TimersWithActivity>>

    @Transaction
    @Query("SELECT * FROM activities")
    suspend fun getActivitiesWithTimers(): List<ActivityWithTimers>

    @Transaction
    @Query("SELECT * FROM timer_tracking_activities WHERE visible = 1")
    suspend fun getVisibleTimersWithActivities(): List<TimersWithActivity>

    @Transaction
    @Query("SELECT * FROM timer_tracking_activities WHERE visible = 1")
    fun observeVisibleTimersWithActivities(): Flow<List<TimersWithActivity>>

    @Transaction
    @Query("""
        SELECT * FROM timer_tracking_activities 
        WHERE date(start_time) BETWEEN date(:startDate) AND date(:endDate) AND
        done = 1
        ORDER BY start_time ASC
    """)
    fun observeTimersByDateRangeWithActivities(startDate: String, endDate: String): Flow<List<TimersWithActivity>>

    @Transaction
    @Query("""
        SELECT a.*, SUM(t.elapsed_time) AS total_elapsed_time
        FROM activities a
        INNER JOIN timer_tracking_activities t ON a.activity_id = t.activity_id
        AND date(t.start_time) BETWEEN date(:startDate) AND date(:endDate)
        GROUP BY a.activity_id
    """)
    fun observeActivitiesTotalTime(startDate: String, endDate: String): Flow<List<ActivityTotalTimeQuery>>

    @Transaction
    @Query("""
       SELECT t.*, a.name AS name_activity
        FROM timer_tracking_activities t
        INNER JOIN activities a ON t.activity_id = a.activity_id
        WHERE t.done = 1
        ORDER BY a.name, date(t.start_time)
    """)
    fun observeAllTimersTracking(): Flow<List<TimerTrackingQuery>>



}
