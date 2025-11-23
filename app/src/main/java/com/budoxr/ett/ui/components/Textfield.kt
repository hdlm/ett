package com.budoxr.ett.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.budoxr.ett.ui.theme.grayLight
import com.budoxr.ett.ui.theme.placeholder


@Composable
fun Textfield(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    trailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val surfaceBorder = if (isDarkTheme) {
        BorderStroke(
            width = 1.dp,
            color = grayLight
        )
    } else {
        null  // no border in light mode
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        border = surfaceBorder
    ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = textLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = placeholder
                )
            },
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                    disabledIndicatorColor = MaterialTheme.colorScheme.background,
                    errorIndicatorColor = MaterialTheme.colorScheme.error,
                )

        )
    }
}


@Composable
@Preview(showBackground = true)
private fun TextfieldPreview() {
    val focusManager: FocusManager = LocalFocusManager.current

    EasyTimeTrackingTheme(darkTheme = true, dynamicColor = false) {
        Surface(modifier = Modifier
            .padding(8.dp)
        ) {
            Textfield(
                isDarkTheme = false,
                value = "My activity",
                onValueChange = { _ ->},
                textLabel = "Name",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                imeAction = ImeAction.Next,
            )

        }
    }

}