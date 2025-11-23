package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme


@Composable
fun FieldFormText(
    label: String,
    hintLabel: String,
    field: String,
    onValueChange: (String) -> Unit,
) {
    val focusManager: FocusManager = LocalFocusManager.current

    val lineSpacing = dimensionResource(R.dimen.line_spacing_1)

    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall
    )
    Spacer(modifier = Modifier.height(lineSpacing))
    Textfield(
        value = field,
        textLabel = hintLabel,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Text,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        imeAction = ImeAction.Next,
    )
}


@Composable
@Preview(showBackground = true)
private fun FieldFormTextPreview() {
    EasyTimeTrackingTheme(darkTheme = true, dynamicColor = false) {
        Surface(modifier = Modifier.padding(8.dp)) {
            FieldFormText(
                label = "Name",
                hintLabel = "Input your activity name",
                field = "My activity",
                onValueChange = {}
            )

        }
    }

}