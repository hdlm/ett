package com.budoxr.ett.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.utils.TimeUtils
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
    onClick: (TimerTrackingModel) -> Unit
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
                onClick = onClick,
                onDismiss = onDismiss
            )
        }

    }


}


@Composable
private fun TimerTrackingBottomSheetContent(
    timerTrackingSelected: TimerTrackingModel,
    onClick: (TimerTrackingModel) -> Unit,
    onDismiss: onDismissType,
    modifier: Modifier = Modifier
) {
    val lineSpacing = dimensionResource(R.dimen.line_spacing_1)
    val lineSpacing2x = dimensionResource(R.dimen.line_spacing_2)
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)

    val startTimeFields = timerTrackingSelected.startTime.substringAfter(" ").split(":")
    val endTimeFields = timerTrackingSelected.endTime?.substringAfter(" ")?.split(":") ?: startTimeFields

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
        Timber.tag(TAG).d("onConfirmClick() -> invoked.")
        val startTimeUpdated = "${timerTrackingSelected.startTime.substringBefore(" ")} ${startTimeDigits.first.toString().padStart(2, '0')}:${startTimeDigits.second.toString().padStart(2, '0')}:${startTimeDigits.third.toString().padStart(2, '0')}"
        val endTimeUpdated = "${timerTrackingSelected.startTime.substringBefore(" ")} ${endTimeDigits.first.toString().padStart(2, '0')}:${endTimeDigits.second.toString().padStart(2, '0')}:${endTimeDigits.third.toString().padStart(2, '0')}"
        val timerTrackingUpdated = timerTrackingSelected.copy(
            startTime = startTimeUpdated,
            endTime = endTimeUpdated,
            elapsedTime = TimeUtils.calculateTimestampDifference(startTimeUpdated, endTimeUpdated),
            visible = false,
        )
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
    val isDarkMode = false

    EasyTimeTrackingTheme(darkTheme = isDarkMode, dynamicColor = false) {
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            TimerTrackingBottomSheetContent(
                timerTrackingSelected = timerSelected,
                onClick = {},
                onDismiss = {}
            )
        }

    }

}

private const val TAG = "che.TimerTrackingBottomSheet"