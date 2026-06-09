package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.budoxr.ett.commons.onDismissType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerBottomSheet(
    onDismiss: onDismissType,
    onRangeSelected: (startDate: String, endDate: String) -> Unit
) {
    // Hoist the bottom sheet configuration state
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    // Hoist the DateRangePicker calendar state
    val rangePickerState = rememberDateRangePickerState()

    // Performance Optimized validation tracking
    val isConfirmEnabled by remember {
        derivedStateOf {
            rangePickerState.selectedStartDateMillis != null &&
                    rangePickerState.selectedEndDateMillis != null
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header actions panel within the sheet
            TextButton(
                onClick = {
                    val start = rangePickerState.selectedStartDateMillis
                    val end = rangePickerState.selectedEndDateMillis
                    if (start != null && end != null) {
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault())
                        val startStr = formatter.format(Instant.ofEpochMilli(start))
                        val endStr = formatter.format(Instant.ofEpochMilli(end))

                        onRangeSelected(startStr, endStr)
                    }
                },
                enabled = isConfirmEnabled,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp, top = 8.dp)
            ) {
                Text("Confirm")
            }

            DateRangePicker(
                state = rangePickerState,
                modifier = Modifier.weight(1f, fill = false),
                title = {
                    Text(
                        text = "Select travel dates",
                        modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
                    )
                }
            )
        }
    }
}
