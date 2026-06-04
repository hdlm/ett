package com.budoxr.ett.presentation.domain

import androidx.compose.ui.graphics.Color

data class ActivityModel(
    val activityId: Long? = null,
    val name: String,
    val color: Color? = null,
)
