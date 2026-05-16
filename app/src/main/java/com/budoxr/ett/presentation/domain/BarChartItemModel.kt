/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.domain

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color



@Immutable
data class BarChartItemModel(
    val key: Any,
    val value: Float,
    val label: String,
    val color: Color
)


