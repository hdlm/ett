/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.budoxr.ett.presentation.domain.BarChartItemModel

@Composable
fun PureBarChart(
    items: List<BarChartItemModel>,
    modifier: Modifier = Modifier,
    height: Dp = 240.dp
) {
    if (items.isEmpty()) return

    val maxVal = items.maxOf { it.value }.coerceAtLeast(1f)
    val textPaint = Paint().apply {
        color = MaterialTheme.colorScheme.onBackground.hashCode()
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Reserve bottom space for labels
        val labelReserveHeight = 50f
        val chartHeight = canvasHeight - labelReserveHeight

        val barCount = items.size
        val totalSpacingPercent = 0.3f // 30% spacing between bars

        val availableWidthForBars = canvasWidth * (1f - totalSpacingPercent)
        val barWidth = availableWidthForBars / barCount
        val spacingWidth = (canvasWidth * totalSpacingPercent) / (barCount + 1)

        items.forEachIndexed { index, item ->
            val left = spacingWidth + index * (barWidth + spacingWidth)
            val barHeight = (item.value / maxVal) * chartHeight
            val top = chartHeight - barHeight

            // Render the Bar
            drawRect(
                color = item.color,
                topLeft = Offset(x = left, y = top),
                size = Size(width = barWidth, height = barHeight)
            )

            // Render Text using Native Canvas context safely
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(
                    item.label,
                    left + (barWidth / 2f),
                    canvasHeight - 10f,
                    textPaint
                )
            }
        }
    }
}