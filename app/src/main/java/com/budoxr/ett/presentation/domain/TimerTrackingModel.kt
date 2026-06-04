package com.budoxr.ett.presentation.domain

data class TimerTrackingModel (
    val timerTrackingId: Long? = null,
    val startTime: String,
    val endTime: String? = null,
    val elapsedTime: Long = 0,
    val visible: Boolean = true,
    val done: Boolean = false,
    val activityId: Long
)