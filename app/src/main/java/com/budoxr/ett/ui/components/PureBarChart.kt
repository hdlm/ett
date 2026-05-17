/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import android.graphics.Paint
import com.budoxr.ett.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.budoxr.ett.presentation.domain.BarChartItemModel
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PureBarChart(
    items: List<BarChartItemModel>,
    modifier: Modifier = Modifier,
    height: Dp = 240.dp
) {
    if (items.isEmpty()) return

    // Define a palette of colors to assign to bars
    val palette = listOf(
        Color(0xFFF44336), Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF673AB7),
        Color(0xFF3F51B5), Color(0xFF2196F3), Color(0xFF03A9F4), Color(0xFF00BCD4),
        Color(0xFF009688), Color(0xFF4CAF50), Color(0xFF8BC34A), Color(0xFFCDDC39),
        Color(0xFFFFEB3B), Color(0xFFFFC107), Color(0xFFFF9800), Color(0xFFFF5722)
    )

    // Assign stable random colors to items based on their index
    val itemsWithColors = remember(items) {
        val shuffledPalette = palette.shuffled()
        items.mapIndexed { index, item ->
            item.copy(color = shuffledPalette[index % shuffledPalette.size])
        }
    }

    val yAxisLabel = stringResource(R.string.label_y_axis)
    val maxVal = itemsWithColors.maxOf { it.value }.coerceAtLeast(1f)
    val onBackground = MaterialTheme.colorScheme.onBackground
    val textPaint = Paint().apply {
        color = onBackground.toArgb()
        textSize = 28f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Reserved space for labels and titles
            val yAxisWidth = 100f
            val xAxisHeight = 20f // Reduced since labels are now in the legend
            val titleSpace = 40f
            
            val leftOffset = yAxisWidth + titleSpace
            val bottomOffset = xAxisHeight
            
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

            // 2. Draw Y-axis (Time hh:mm) and Grid
            val ySteps = 5
            val gridColor = onBackground.copy(alpha = 0.1f)
            for (i in 0..ySteps) {
                val ratio = i / ySteps.toFloat()
                val yValue = maxVal * ratio
                val yPos = chartHeight * (1f - ratio)
                
                // Horizontal grid line
                if (i > 0) {
                    drawLine(
                        color = gridColor,
                        start = Offset(leftOffset, yPos),
                        end = Offset(canvasWidth, yPos),
                        strokeWidth = 1f
                    )
                }

                // Ticks
                drawLine(
                    color = onBackground,
                    start = Offset(leftOffset - 10f, yPos),
                    end = Offset(leftOffset, yPos),
                    strokeWidth = 2f
                )

                // Value labels (Formatted as hh:mm)
                drawIntoCanvas { canvas ->
                    val totalSeconds = yValue.toInt()
                    val hours = totalSeconds / 3600
                    val minutes = (totalSeconds % 3600) / 60
                    val timeLabel = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)

                    canvas.nativeCanvas.drawText(
                        timeLabel,
                        leftOffset - 20f,
                        yPos + 10f,
                        textPaint.apply { textAlign = Paint.Align.RIGHT }
                    )
                }
            }

            // Y-axis Title
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

            // 3. Draw Bars
            val barCount = itemsWithColors.size
            val barSpacingPercent = 0.2f
            val totalBarWidth = chartWidth * (1f - barSpacingPercent)
            val barWidth = totalBarWidth / barCount
            val spacing = (chartWidth * barSpacingPercent) / (barCount + 1)

            itemsWithColors.forEachIndexed { index, item ->
                val left = leftOffset + spacing + index * (barWidth + spacing)
                val barHeight = (item.value / maxVal) * chartHeight
                val top = chartHeight - barHeight

                drawRect(
                    color = item.color,
                    topLeft = Offset(x = left, y = top),
                    size = Size(width = barWidth, height = barHeight)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Legend
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsWithColors.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color = item.color, shape = CircleShape)
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
