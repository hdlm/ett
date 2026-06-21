/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import timber.log.Timber

@Composable
fun SearchField(
    value: String,
    onValueChange: onStringType,
    placeholder: String,
    modifier: Modifier,
) {
    val iconSize = dimensionResource(id = R.dimen.icon_small_size)

    // Using TextFieldValue locally to manage cursor position and sync with external state
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    // Update local state when external value changes (e.g., cleared by parent)
    LaunchedEffect(value) {
        if (value != textFieldValueState.text) {
            textFieldValueState = textFieldValueState.copy(
                text = value,
                selection = TextRange(value.length)
            )
        }
    }

    val onCancelClick: onDismissType = {
        Timber.tag(TAG).i("onCancelClick() -> invoked")
        textFieldValueState = TextFieldValue("")
        onValueChange("")
    }

    val showSearchCancelButton = textFieldValueState.text.isNotEmpty()

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 3.dp,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        ) {
            BasicTextField(
                value = textFieldValueState,
                onValueChange = {
                    textFieldValueState = it
                    if (it.text != value) {
                        onValueChange(it.text)
                    }
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.content_description_icon),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(iconSize)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (textFieldValueState.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            innerTextField()
                        }
                    }
                }
            )
        }
        if (showSearchCancelButton) {
            IconButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(iconSize)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = stringResource(id = R.string.content_description_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = false)
fun SearchFieldPreview() {
    val value = ""
    val onValueChange: onStringType = {  _ -> }
    
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        ) {
            SearchField(
                value = value,
                onValueChange = onValueChange,
                placeholder = stringResource(id = R.string.label_search_field_placeholder),
                modifier = Modifier
            )
        }

    }
}

private const val TAG = "che.SearchField"