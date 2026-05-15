/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
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
    @Query("SELECT * FROM timer_tracking_activities WHERE visible = 1")
    suspend fun getVisibleTimersWithActivities(): List<TimersWithActivity>

    @Transaction
    @Query("SELECT * FROM timer_tracking_activities WHERE visible = 1")
    fun observeVisibleTimersWithActivities(): Flow<List<TimersWithActivity>>


}