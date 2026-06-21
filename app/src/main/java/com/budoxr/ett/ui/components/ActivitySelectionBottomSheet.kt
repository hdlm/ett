/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onLongType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.data.database.entities.ActivityEntity
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySelectionBottomSheet(
    sheetState: SheetState,
    onDismiss: onDismissType,
    placeholderActivities: List<ActivityEntity>,
    onActivitySelected: onLongType,
    search: String,
    onSearchChange: onStringType,
) {
    val lineSpacing1x = dimensionResource(id = R.dimen.line_spacing_1)
    val lineSpacing2x = dimensionResource(id = R.dimen.line_spacing_2)
    val lineSpacing3x = dimensionResource(id = R.dimen.line_spacing_3)

    Timber.tag(TAG).i("compose / recompose")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.padding(top = 24.dp) // Optional: adjust padding
    ) {
        Surface(modifier = Modifier) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchField(
                    value = search,
                    onValueChange = onSearchChange,
                    placeholder = stringResource(R.string.label_search_field_placeholder),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(vertical = lineSpacing1x))

                Text(
                    text = stringResource(R.string.label_select_activity),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = lineSpacing2x)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = placeholderActivities,
                        key = { it.activityId!! }
                    ) { activity ->
                        ActivityListItem(
                            activity = activity,
                            onItemClick = { id -> onActivitySelected(id) }
                        )
                        HorizontalDivider(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = lineSpacing1x)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(lineSpacing3x))
            }
        }

    }
}


@Composable
fun ActivityListItem(
    activity: ActivityEntity,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = activity.name,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.PlayCircle,
                contentDescription = stringResource(R.string.content_description_start_timer),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(activity.activityId!!) }
    )
}

private const val TAG = "che.ActivitySelectionBottomSheet"