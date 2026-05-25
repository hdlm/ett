/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.commons.utils.TimeUtils.formatElapsedTime
import com.budoxr.ett.commons.utils.TimeUtils.toTimestampFormat
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.commons.utils.toEpochMillis
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.ui.MonitorState
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import kotlinx.coroutines.delay

@Composable
fun MonitorScreenRowItem(
    monitorState: MonitorState,
    nameActivity: String,
    item: TimerTrackingEntity,
    formatLastThreeDigits: (Long) -> String,
    modifier: Modifier
) {

    val iconSize = dimensionResource(id = R.dimen.icon_medium_size)
    val playIcon = Icons.Filled.PlayCircle
    val stopIcon = Icons.Filled.StopCircle
    val separation = dimensionResource(R.dimen.side_separation)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .weight(0.6f)
        ) {
            Row {
                Text(text = "(${formatLastThreeDigits(item.timerTrackingId!!)})", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(separation))
                Text(text = nameActivity, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Column (modifier = Modifier
            .weight(0.3f)
        ) {
            if (item.endTime == null) {
                RealTimeTimer(
                    startTimestamp = item.startTime.toEpochMillis(TimeUtils.timestampFormatter),
                )
            } else {
                Text(
                    text = item.elapsedTime.toTimestampFormat(),
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Column (modifier = Modifier
            .weight(0.1f)
        ) {
            if (!item.done) {
                IconButton(
                    onClick = { monitorState.onStopClick(item.timerTrackingId!!)},
                    enabled = true
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        imageVector = stopIcon,
                        contentDescription = stringResource(id = R.string.content_description_icon),
                        tint = MaterialTheme.colorScheme.primary, //standard icon color (onSurfaceVariant)
                    )
                }
            } else {
                Checkbox(
                    checked = item.visible,
                    onCheckedChange = {
                        monitorState.onHideTimer.invoke(item.timerTrackingId!!)
                    }
                )
            }
        }

    }

}


@Composable
fun RealTimeTimer(startTimestamp: Long) {
    // 1. State to hold the formatted string
    var timeDisplay by remember { mutableStateOf("00:00:00") }

    // 2. Side effect to run the timer logic
    LaunchedEffect(startTimestamp) {
        while (true) {
            timeDisplay = formatElapsedTime(startTimestamp)
            delay(1000L) // Wait 1 second
        }
    }

    // 3. UI Display
    Text(
        text = timeDisplay,
        style = MaterialTheme.typography.labelLarge,
        fontFamily = FontFamily.Monospace
    )
}


@Composable
@Preview(showBackground = true)
fun MonitorScreenRowItemPreview() {
    val utility = Utility(LocalContext.current)
    val isDarkTheme = false
    
    val monitorState = MonitorState(
        navController = rememberNavController(),
        isDarkTheme = isDarkTheme,
        isRefreshing = false,
        onRefresh = {},
        onNewTimerClick = {},
        activeTimers = emptyList(),
        historicalTimers = emptyList(),
        onStartClick = {_ ->},
        onStopClick = {_ ->},
        onDeleteClick = {_ ->},
        onHideTimer = { _ ->},
        onBackButtonClick = {},
        onSelectedView = { _ ->},
    )

    val activity = ActivityEntity(
        activityId = 1,
        name = "DRIBBLI",
        color = Color(-34757177461702656)
    )
    val item = TimerTrackingEntity(
        timerTrackingId = 1,
        startTime = "2026-05-15 06:57:09",
        endTime = null,
        elapsedTime = 0,
        visible = true,
        done = false,
        activityId = 1
    )
    
    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background)
        {
            MonitorScreenRowItem(
                monitorState = monitorState,
                nameActivity = activity.name,
                item = item,
                formatLastThreeDigits = utility::formatLastThreeDigits,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private const val TAG = "che.MonitorScreenRowItem"