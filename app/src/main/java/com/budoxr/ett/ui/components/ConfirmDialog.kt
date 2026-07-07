/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    labelButtonConfirm: String = stringResource(R.string.label_confirm_button_yes),
    labelButtonDismiss: String = stringResource(R.string.label_confirm_button_no),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        icon = { 
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = labelButtonConfirm)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = labelButtonDismiss)
            }
        }
        
    )
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDialogPreview() {
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {

        Surface(modifier = Modifier.padding(8.dp)) {
            ConfirmDialog(
                title = stringResource(R.string.title_modify_record),
                message = stringResource(R.string.msg_modify_record),
                onDismiss = {},
                onConfirm = {}
            )
        }

    }
}
