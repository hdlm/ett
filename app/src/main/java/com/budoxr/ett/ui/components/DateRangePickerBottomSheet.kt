package com.budoxr.ett.ui.components

import com.budoxr.ett.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.budoxr.ett.commons.onDismissType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerBottomSheet(
    onDismiss: onDismissType,
    onRangeSelected: (Pair<String,String>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val rangePickerState = rememberDateRangePickerState()

    val isConfirmEnabled by remember {
        derivedStateOf {
            rangePickerState.selectedStartDateMillis != null &&
                    rangePickerState.selectedEndDateMillis != null
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.padding(top = 24.dp) // Optional: adjust padding
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
                        val formatter = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.of("UTC"))
                        val startStr = formatter.format(Instant.ofEpochMilli(start))
                        val endStr = formatter.format(Instant.ofEpochMilli(end))

                        onRangeSelected.invoke(Pair(startStr, endStr))
                    }
                },
                enabled = isConfirmEnabled,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp, top = 0.dp)
            ) {
                Text(
                    stringResource(R.string.label_confirm),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            DateRangePicker(
                state = rangePickerState,
                modifier = Modifier.weight(1f, fill = false),
                title = {
                    Text(
                        text = stringResource(R.string.label_select_date_range),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
                    )
                },
            )
        }
    }
}
