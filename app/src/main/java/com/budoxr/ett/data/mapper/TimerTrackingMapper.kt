package com.budoxr.ett.data.mapper

import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.presentation.domain.TimerTrackingModel

fun TimerTrackingEntity.toModel() =
    TimerTrackingModel(
        timerTrackingId = timerTrackingId,
        startTime = startTime,
        endTime = endTime,
        elapsedTime = elapsedTime,
        visible = visible,
        done = done,
        activityId = activityId
    )

fun TimerTrackingModel.toEntity() =
    TimerTrackingEntity(
        timerTrackingId = timerTrackingId,
        startTime = startTime,
        endTime = endTime,
        elapsedTime = elapsedTime,
        visible = visible,
        done = done,
        activityId = activityId
    )
