/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import com.budoxr.ett.commons.toLocalDate
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.presentation.presenters.CsvFields
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import timber.log.Timber
import java.time.DayOfWeek

class CsvHelper {

    /**
     * Processes the CSV raw data and inserts it into the database.
     * @param rawData The CSV raw data as a string.
     */
    suspend fun processCsvRawData(
        rawData: String,
        activities: Set<ActivityEntity>,
        activityInsertUseCase: ActivityInsertUseCase,
        timerTrackingUseCase: TimerTrackingInsertUseCase,
    ) {
        val records = rawData.lineSequence()
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .toList()

        records.forEach { record ->
            Timber.tag(TAG).d("processCsvRawData() -> Record: $record")
            val fields = record.split(";")
            if (fields.size != CsvFields.entries.size) return@forEach

            val activityName = fields[CsvFields.Activity.value]
            
            // Skip header row if detected
            if (activityName.equals("A", ignoreCase = true) || activityName.equals("Activity", ignoreCase = true)) {
                return@forEach
            }

            val csvStartPeriod = try { fields[CsvFields.Sunday.value].toLocalDate() } catch (e: Exception) { null }
            val csvEndPeriod = try { fields[CsvFields.Saturday.value].toLocalDate() } catch (e: Exception) { null }

            if (csvStartPeriod != null && csvEndPeriod != null &&
                csvStartPeriod.isBefore(csvEndPeriod) &&
                DayOfWeek.SUNDAY == csvStartPeriod.dayOfWeek &&
                DayOfWeek.SATURDAY == csvEndPeriod.dayOfWeek
            ) {
                Timber.tag(TAG).d("processCsvRawData() -> Inserting/Getting activity: $activityName")
                val activity = activities.find { it.name.lowercase() == activityName.lowercase() }
                val activityId: Long = if (activity != null) {
                    activity.activityId ?: -1L
                } else {
                    activityInsertUseCase.invoke(ActivityEntity(name = activityName))
                }

                if (activityId == -1L)  {
                    Timber.tag(TAG).e("processCsvRawData() -> Error inserting/getting activity: $activityName")
                    return@forEach
                }

                // Process all 7 days of the week (indices 1 to 7 for values)
                for (i in 0 until 7) {
                    val fieldValue = fields[i + 1]
                    val minutes: Long = if (fieldValue.isBlank()) 0L else fieldValue.toLong()
                    
                    if (minutes > 0) {
                        val newDate = csvStartPeriod.plusDays(i.toLong())
                        val newDateTime = newDate.atTime(23, 59, 59)

                        val elapsedTimeInSeconds = minutes * 60L
                        val endDateTime = newDateTime.plusSeconds(elapsedTimeInSeconds)

                        timerTrackingUseCase.invoke(
                            TimerTrackingEntity(
                                startTime = newDateTime.toTimestamp(),
                                endTime = endDateTime.toTimestamp(),
                                activityId = activityId,
                                visible = false,
                                done = true
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "che.CsvHelper"
    }
}
