/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.utils.TimeUtils.toTimestampFormat
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.ui.MonitorState
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun MonitorScreenRowHistoricalItem(
    monitorState: MonitorState,
    item: TimerTrackingQuery,
    formatLastThreeDigits: (Long) -> String,
    modifier: Modifier
) {

    val iconSize = dimensionResource(id = R.dimen.icon_small_size)
    val deleteIcon = Icons.Filled.Delete
    val separation = dimensionResource(R.dimen.side_separation)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column {
            Row {
                Text(text = "(${formatLastThreeDigits(item.timerTracking.timerTrackingId!!)})", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Column {
            Row {
                Text(text = item.timerTracking.startTime.substringBefore(" "), style = MaterialTheme.typography.bodyMedium)
            }

        }

        Column {
            Row {
                Text(
                    text = " ${item.timerTracking.elapsedTime.toTimestampFormat()}",
                    style = MaterialTheme.typography.labelMedium,
                    fontFamily = FontFamily.Monospace
                )
            }

        }

        Column {
            IconButton(
                onClick = { monitorState.onDeleteClick(item.timerTracking.timerTrackingId!!)},
                enabled = true
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = deleteIcon,
                    contentDescription = stringResource(id = R.string.content_description_icon),
                    tint = MaterialTheme.colorScheme.primary, //standard icon color (onSurfaceVariant)
                )
            }

        }

    }

}


@Composable
@Preview(showBackground = true)
fun MonitorScreenRowHistoricalItemPreview() {
    val context = LocalContext.current
    val isDarkTheme = false
    val utily = Utility(context)

    val monitorState = MonitorState(
        navController = rememberNavController(),
        isDarkTheme = isDarkTheme,
        isRefreshing = false,
        onSearchChange = {},
        onRefresh = {},
        onNewTimerClick = {},
        activeTimers = emptyList(),
        historicalTimers = emptyList(),
        onStartClick = { _ -> },
        onStopClick = { _ -> },
        onDeleteClick = { _ -> },
        onHideTimer = { _ -> },
        onBackButtonClick = {},
        onSelectedView = { _ -> },
        onSaveTimerTracking = { _ -> },
        onActivityClick = { _ -> },
    )

    val timerTrackingEntity = TimerTrackingEntity(
        timerTrackingId = 1,
        startTime = "2026-05-15 06:57:09",
        endTime = null,
        elapsedTime = 0,
        visible = true,
        done = false,
        activityId = 1
    )
    val item = TimerTrackingQuery(
        timerTracking = timerTrackingEntity,
        nameActivity = "DRIBBLI"
    )

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background)
        {
            MonitorScreenRowHistoricalItem(
                monitorState = monitorState,
                item = item,
                formatLastThreeDigits = utily::formatLastThreeDigits,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private const val TAG = "che.MonitorScreenRowHistoricalItem"