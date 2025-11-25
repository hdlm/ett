package com.budoxr.ett.ui.components

import androidx.compose.foundation.clickable
import com.budoxr.ett.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySelectionBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onActivitySelected: (activityId: Long) -> Unit
) {
    // Replace this placeholder list with a list of your actual Activity data objects (e.g., ActivityEntity)
    val placeholderActivities = listOf(
        "Project X - Development",
        "Learning Compose",
        "Client Follow-up",
        "Documentation"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.padding(top = 24.dp) // Optional: adjust padding
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.label_select_activity), // Define this string resource
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // List of activities
            LazyColumn {
                itemsIndexed(placeholderActivities) { index, activityName ->
                    ListItem(
                        headlineContent = { Text(activityName) },
                        trailingContent = {
                            Icon(Icons.Filled.PlayCircle, contentDescription = stringResource(R.string.content_description_start_timer)) // Define this string resource
                        },
                        modifier = Modifier.clickable {
                            // Example of passing a placeholder ID (index + 1)
                            onActivitySelected(index.toLong() + 1)
                        }
                    )
                    Divider()
                }
            }
            // Add Spacer for bottom padding under the last item
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}