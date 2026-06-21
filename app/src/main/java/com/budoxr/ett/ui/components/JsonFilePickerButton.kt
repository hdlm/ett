/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun JsonFilePickerButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onFileSelected: (Uri) -> Unit,
    label: String = stringResource(R.string.label_select_csv_file)
) {
    val context = LocalContext.current

    // Using OpenDocument contract because it allows specifying multiple MIME types.
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            // Fix: Take persistable URI permission to avoid SecurityException on some devices (like Samsung)
            // when the URI is accessed later in a background thread.
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: Exception) {
                // Not all providers support persistable permissions, but SAF usually does.
            }
            onFileSelected(uri)
        }
    }

    ButtonConfirm (
        label = label,
        isEnabled = isEnabled,
        showTopBorderLine = false,
        buttonIcon = null,
        buttonVector = null,
        buttonImg = null,
        onConfirmClick = {
            val mimeTypes = arrayOf(
                "text/json",
                "application/json",
                "text/plain",
            )
            filePickerLauncher.launch(mimeTypes)
        },
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun JsonFilePickerButtonPreview() {
    val label = "Select a JSON File"
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface( modifier = Modifier.padding(8.dp)) {
            JsonFilePickerButton(
                modifier = Modifier,
                label = label,
                isEnabled = true,
                onFileSelected = {}
            )
        }
    }
}
