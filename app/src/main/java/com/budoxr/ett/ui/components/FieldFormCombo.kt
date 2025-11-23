package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun FieldFormCombo(
    modifier: Modifier,
    isDarkTheme: Boolean,
    items: Array<String>,
    label: String,
    field: String,
    enabled: Boolean = true,
    onSelectedItem: onIntType,
) {
    val value = remember { mutableStateOf(TextFieldValue(field)) }

    ComboBox(
        isDarkTheme = isDarkTheme,
        items = items,
        label = label,
        field = value,
        enabled = enabled,
        onSelectedItem = onSelectedItem,
        modifier = Modifier
    )

}


@Composable
@Preview(showBackground = true)
private fun FieldFormComboPreview() {
    val items = arrayOf("Item 1", "Item 2", "Item 3")
    val label = "Color"
    val field = "Item 2"

    val isDarkTheme = true
    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {

        Surface(modifier = Modifier.padding(8.dp)) {
            FieldFormCombo(
                modifier = Modifier,
                isDarkTheme = isDarkTheme,
                items = items,
                label = label,
                field = field,
                enabled = true,
                onSelectedItem = { _ ->}
            )
        }
    }

}