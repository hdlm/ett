/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.commons.utils.TimeUtils.toTimestampFormat
import com.budoxr.ett.presentation.domain.ActivityModel
import com.budoxr.ett.presentation.domain.TimerTrackingModel
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import io.dynamiteapps.dribbli.ui.components.TimerPickerGroup
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTrackingBottomSheet(
    sheetState: SheetState,
    onDismiss: onDismissType,
    timerTrackingSelected: TimerTrackingModel,
    showActivityList: Boolean,
    activities: List<ActivityModel>,
    onClick: (TimerTrackingModel) -> Unit,
    onActivityClick: (ActivityModel) -> Unit
) {
    Timber.tag(TAG).i("compose / recompose")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TimerTrackingBottomSheetContent(
                timerTrackingSelected = timerTrackingSelected,
                showActivityList = showActivityList,
                activities = activities,
                onClick = onClick,
                onActivityClick = onActivityClick,
                onDismiss = onDismiss,
                modifier = Modifier,
            )
        }
    }

}


@Composable
private fun TimerTrackingBottomSheetContent(
    timerTrackingSelected: TimerTrackingModel,
    showActivityList: Boolean,
    activities: List<ActivityModel>,
    onClick: (TimerTrackingModel) -> Unit,
    onActivityClick: (ActivityModel) -> Unit,
    onDismiss: onDismissType,
    modifier: Modifier = Modifier
) {
    val lineSpacing = dimensionResource(R.dimen.line_spacing_1)
    val lineSpacing2x = dimensionResource(R.dimen.line_spacing_2)
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)

    val startTimeFields = timerTrackingSelected.startTime.substringAfter(" ").split(":")
    val endTimeFields = timerTrackingSelected.endTime?.substringAfter(" ")?.split(":") ?: startTimeFields

    var activity by remember { mutableStateOf(activities.find { it.activityId == timerTrackingSelected.activityId }) }
    var startTimeDigits by remember { mutableStateOf(
        if (startTimeFields.size == 3) {
            Triple(startTimeFields[0].toInt(), startTimeFields[1].toInt(), startTimeFields[2].toInt())
        } else {
            Triple(0, 0, 0)
        }
    ) }
    var endTimeDigits by remember { mutableStateOf(
        if (endTimeFields.size == 3) {
            Triple(endTimeFields[0].toInt(), endTimeFields[1].toInt(), endTimeFields[2].toInt())
        } else {
            Triple(0, 0, 0)
        }
    ) }

    //region high-order functions
    val onHourStartTimeChange: onIntType = { newValue ->
        startTimeDigits = startTimeDigits.copy(first = newValue)
    }
    val onHourEndTimeChangeChange: onIntType = { newValue ->
        endTimeDigits = endTimeDigits.copy(first = newValue)
    }
    val onMinuteStartTimeChange: onIntType = { newValue ->
        startTimeDigits = startTimeDigits.copy(second = newValue)
    }
    val onMinuteEndTimeChangeChange: onIntType = { newValue ->
        endTimeDigits = endTimeDigits.copy(second = newValue)
    }
    val onSecondStartTimeChange: onIntType = { newValue ->
        startTimeDigits = startTimeDigits.copy(third = newValue)
    }
    val onSecondEndTimeChangeChange: onIntType = { newValue ->
        endTimeDigits = endTimeDigits.copy(third = newValue)
    }
    val onConfirmClick: onDismissType = {
        Timber.tag(TAG).d("onConfirmClick() -> invoked, timerTrackingId: ${timerTrackingSelected.timerTrackingId}")
        val startTimeUpdated = "${timerTrackingSelected.startTime.substringBefore(" ")} ${startTimeDigits.first.toString().padStart(2, '0')}:${startTimeDigits.second.toString().padStart(2, '0')}:${startTimeDigits.third.toString().padStart(2, '0')}"
        val endTimeUpdated = "${timerTrackingSelected.endTime?.substringBefore(" ") ?: timerTrackingSelected.startTime.substringBefore(" ")} ${endTimeDigits.first.toString().padStart(2, '0')}:${endTimeDigits.second.toString().padStart(2, '0')}:${endTimeDigits.third.toString().padStart(2, '0')}"
        val timerTrackingUpdated = timerTrackingSelected.copy(
            startTime = startTimeUpdated,
            endTime = endTimeUpdated,
            elapsedTime = TimeUtils.calculateTimestampDifference(startTimeUpdated, endTimeUpdated),
            visible = false,
        )
        Timber.tag(TAG).i("confirm the changes:\n\t-> startTime: $startTimeUpdated\n\t-> endTime: $endTimeUpdated\n\t-> elapsedTime: ${timerTrackingUpdated.elapsedTime.toTimestampFormat()} (${timerTrackingUpdated.elapsedTime}s)")
        onClick.invoke(timerTrackingUpdated)
        onDismiss.invoke()
    }
    //endregion


    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.label_save_timer),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = lineSpacing2x)
        )

        if (showActivityList) {
            Text(
                text = stringResource(R.string.label_select_activity),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = lineSpacing)
            )

            TimerTrackingActivityList(
                activities = activities,
                onActivityClick = {
                    activity = it
                    onActivityClick.invoke(it)
                }
            )

        } else {
            Text(
                text = stringResource(R.string.label_activity),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = lineSpacing)
            )

            TextButton(
                onClick = { onActivityClick.invoke(activity!!) }
            ) {
                Text(
                    text = "${activity?.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.padding(bottom = lineSpacing2x)
                )
            }

            Text(
                text = stringResource(R.string.label_start_timer),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = lineSpacing)
            )

            Text(
                text = timerTrackingSelected.startTime,
                style = MaterialTheme.typography.bodyLarge,
            )

            TimerPickerGroup(
                hours = startTimeDigits.first,
                minutes = startTimeDigits.second,
                seconds = startTimeDigits.third,
                onHoursChange = onHourStartTimeChange,
                onMinutesChange = onMinuteStartTimeChange,
                onSecondsChange = onSecondStartTimeChange,
                modifier = Modifier.padding(bottom = lineSpacing2x)
            )

            Text(
                text = stringResource(R.string.label_end_timer),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(bottom = lineSpacing)
            )

            Text(
                text = timerTrackingSelected.endTime ?: timerTrackingSelected.startTime,
                style = MaterialTheme.typography.bodyLarge,
            )

            TimerPickerGroup(
                hours = endTimeDigits.first,
                minutes = endTimeDigits.second,
                seconds = endTimeDigits.third,
                onHoursChange = onHourEndTimeChangeChange,
                onMinutesChange = onMinuteEndTimeChangeChange,
                onSecondsChange = onSecondEndTimeChangeChange,
            )

            ButtonConfirm(
                modifier = Modifier.padding(top = lineSpacing2x, start = horizontalMargin, end = horizontalMargin),
                label = stringResource(R.string.label_button_save),
                isEnabled = true,
                showTopBorderLine = true,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = onConfirmClick
            )
        }
    }

}


@Composable
private fun TimerTrackingActivityList(
    activities: List<ActivityModel>,
    onActivityClick: (ActivityModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // space between items
    ) {
        activities.forEach { activity ->
            ActivityRowItem(
                activity = activity,
                onItemClick = { onActivityClick(activity) }
            )
        }
    }
}


@Composable
private fun ActivityRowItem(
    activity: ActivityModel,
    onItemClick: onDismissType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = activity.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}


@Composable
@Preview(showBackground = true)
private fun TimerTrackingBottomSheetPreview() {
    val timerSelected = TimerTrackingModel(
        timerTrackingId = 1,
        startTime = "2026-06-03 13:00:00",
        endTime = "2026-06-03 14:00:00",
        elapsedTime = 3600L,
        visible = true,
        done = true,
        activityId = 1
    )
    val activities = listOf(
        ActivityModel(
            activityId = 1,
            name = "Work",
            color = null
        ),
        ActivityModel(
            activityId = 2,
            name = "Study",
            color = null
        )
    )
    val isDarkMode = false


    EasyTimeTrackingTheme(darkTheme = isDarkMode, dynamicColor = false) {
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TimerTrackingBottomSheetContent(
                timerTrackingSelected = timerSelected,
                showActivityList = false,
                activities = activities,
                onClick = {},
                onActivityClick = {},
                onDismiss = {}
            )
        }

    }

}

private const val TAG = "che.TimerTrackingBottomSheet"
