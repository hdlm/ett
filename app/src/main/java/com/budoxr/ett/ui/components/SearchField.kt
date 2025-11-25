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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
    val showSearchCancelButton = value.isNotEmpty()
    val iconSize = dimensionResource(id = R.dimen.icon_small_size)

    val onCancelClick: onDismissType = {
        Timber.tag(TAG).i("onCancelClick() -> invoked")
        onValueChange("")
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Surface(
                modifier = modifier,
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 3.dp,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth()
                                .padding(all = 5.dp),
                            verticalAlignment =  Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.content_description_icon),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(iconSize)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if(value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            } else {
                                innerTextField()
                            }
                        }
                    }
                )
            }
            if (showSearchCancelButton) {
                IconButton(onClick = onCancelClick,
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