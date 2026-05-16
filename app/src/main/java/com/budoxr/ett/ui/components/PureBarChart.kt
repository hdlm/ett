/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import android.graphics.Paint
import com.budoxr.ett.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
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

    val yAxisLabel = stringResource(R.string.label_y_axis)
    val maxVal = items.maxOf { it.value }.coerceAtLeast(1f)
    val onBackground = MaterialTheme.colorScheme.onBackground
    val textPaint = Paint().apply {
        color = onBackground.toArgb()
        textSize = 28f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(top = 16.dp, end = 16.dp) // Leave space for axis labels elsewhere
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Reserved space for labels and titles
        val yAxisWidth = 80f
        val xAxisHeight = 60f
        val titleSpace = 40f
        
        val leftOffset = yAxisWidth + titleSpace
        val bottomOffset = xAxisHeight + titleSpace
        
        val chartWidth = canvasWidth - leftOffset
        val chartHeight = canvasHeight - bottomOffset

        // 1. Draw Axis Lines
        drawLine(
            color = onBackground,
            start = Offset(leftOffset, 0f),
            end = Offset(leftOffset, chartHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = onBackground,
            start = Offset(leftOffset, chartHeight),
            end = Offset(canvasWidth, chartHeight),
            strokeWidth = 2f
        )

        // 2. Draw Y-axis (Seconds)
        val ySteps = 5
        for (i in 0..ySteps) {
            val ratio = i / ySteps.toFloat()
            val yValue = maxVal * ratio
            val yPos = chartHeight * (1f - ratio)
            
            // Ticks
            drawLine(
                color = onBackground,
                start = Offset(leftOffset - 10f, yPos),
                end = Offset(leftOffset, yPos),
                strokeWidth = 2f
            )

            // Value labels
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(
                    yValue.toInt().toString(),
                    leftOffset - 20f,
                    yPos + 10f,
                    textPaint.apply { textAlign = Paint.Align.RIGHT }
                )
            }
        }

        // Y-axis Title: "Seconds"
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.save()
            canvas.nativeCanvas.rotate(-90f, 25f, chartHeight / 2f)
            canvas.nativeCanvas.drawText(
                yAxisLabel,
                25f,
                chartHeight / 2f,
                textPaint.apply { 
                    textAlign = Paint.Align.CENTER
                    textSize = 30f
                }
            )
            canvas.nativeCanvas.restore()
        }

        // 3. Draw Bars and X-axis (Activities)
        val barCount = items.size
        val barSpacingPercent = 0.2f
        val totalBarWidth = chartWidth * (1f - barSpacingPercent)
        val barWidth = totalBarWidth / barCount
        val spacing = (chartWidth * barSpacingPercent) / (barCount + 1)

        items.forEachIndexed { index, item ->
            val left = leftOffset + spacing + index * (barWidth + spacing)
            val barHeight = (item.value / maxVal) * chartHeight
            val top = chartHeight - barHeight

            drawRect(
                color = item.color,
                topLeft = Offset(x = left, y = top),
                size = Size(width = barWidth, height = barHeight)
            )

            // X-axis item label
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(
                    item.label,
                    left + (barWidth / 2f),
                    chartHeight + 35f,
                    textPaint.apply { 
                        textAlign = Paint.Align.CENTER
                        textSize = 24f
                    }
                )
            }
        }
        
        // X-axis Title: "Activities"
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawText(
                "Activities",
                leftOffset + chartWidth / 2f,
                canvasHeight - 10f,
                textPaint.apply { 
                    textAlign = Paint.Align.CENTER
                    textSize = 30f
                }
            )
        }
    }
}
