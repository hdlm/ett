package com.budoxr.ett.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.budoxr.ett.ui.theme.grayDark
import com.budoxr.ett.ui.theme.grayLight

@Composable
fun ComboBox(
    isDarkTheme: Boolean,
    items: Array<String>,
    label: String,
    field: MutableState<TextFieldValue>,
    enabled: Boolean = true,
    onSelectedItem: onIntType,
    modifier: Modifier
) {

    val expanded = remember { mutableStateOf( false ) }
    val selectedIndex = remember { mutableStateOf( 0 ) }

    ComboBoxText(
        isDarkTheme = isDarkTheme,
        modifier = modifier,
        field = field,
        items = items,
        label = label,
        selectedIndex = selectedIndex,
        expanded = expanded,
        enabled = enabled,
        onSelectedItem = onSelectedItem,
    )

}


@Composable
fun ComboBoxIcon(
    icon: ImageVector,
    onIconClick: onDismissType,
    enabled: Boolean = true,
    modifier: Modifier
)
{
    ComboBoxButtonIcon(
        modifier = Modifier,
        icon = icon,
        onIconClick = onIconClick,
        enabled = enabled
    )

}

@Composable
private fun ComboBoxText(
    modifier: Modifier,
    isDarkTheme: Boolean,
    field: MutableState<TextFieldValue>,
    items: Array<String>,
    label: String,
    selectedIndex: MutableState<Int>,
    expanded: MutableState<Boolean>,
    enabled: Boolean = true,
    onSelectedItem: onIntType,
) {
    val separation = dimensionResource(id = R.dimen.side_separation_2x)
    val iconSize = dimensionResource(id = R.dimen.icon_tiny_size)
    var anyPoint by remember { mutableStateOf<Long?>(null) }

    val lineSpacing = dimensionResource(R.dimen.line_spacing_1)

    val surfaceBorder = if (isDarkTheme) {
        BorderStroke(
            width = 1.dp,
            color = grayLight
        )
    } else {
        null  // no border in light mode
    }

    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall
    )
    Spacer(modifier = Modifier.height(lineSpacing))

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
        border = surfaceBorder,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = items[selectedIndex.value],
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .weight(1f)
                    .pointerInput(anyPoint) {
                        awaitEachGesture {
                            // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                            // in the Initial pass to observe events before the text field consumes them
                            // in the Main pass.
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                expanded.value = true
                            }
                        }
                    }
                    .padding(start = separation, top = 17.dp, bottom = 17.dp)
            )
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .size(iconSize),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = R.string.content_description_trailing_icon),
                tint = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant /* Use a standard icon color */ else MaterialTheme.colorScheme.surface
            )

        }

        if (!enabled) {
            expanded.value = false
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items.forEachIndexed { index, item ->

                DropdownMenuItem(
                    text = {
                        Text(modifier = Modifier.padding(horizontal = 16.dp),
                            text = item, style = MaterialTheme.typography.bodyMedium)
                    },
                    onClick = {
                        field.value = TextFieldValue(item)
                        selectedIndex.value = index
                        expanded.value = false
                        onSelectedItem.invoke(index)
                    }
                )
            }

        }
    }
}


@Composable
private fun ComboBoxButtonIcon(modifier: Modifier,
                               icon: ImageVector,
                               onIconClick: onDismissType,
                               enabled: Boolean,
) {
    val separation = dimensionResource(id = R.dimen.side_separation_2x)
    val iconSize = dimensionResource(id = R.dimen.icon_tiny_size)

    Surface(
        shape = RoundedCornerShape(12.dp),
    ) {
        IconButton(
            onClick = { onIconClick.invoke() },
            enabled = enabled
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = icon,
                contentDescription = stringResource(id = R.string.content_description_icon),
                tint = MaterialTheme.colorScheme.secondary,
            )
        }

    }
}


@Composable
@Preview(showBackground = true)
private fun ComboBoxPreview() {
    val items = arrayOf("Item 1", "Item 2", "Item 3")
    val label = "List of Items"
    val field = remember { mutableStateOf(TextFieldValue()) }

    val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)

    val isDarkTheme = true

    EasyTimeTrackingTheme(
        darkTheme = isDarkTheme,
        dynamicColor = false)
    {
        Column(
            modifier = Modifier
                .padding(horizontalMargin)
                .background(MaterialTheme.colorScheme.background)
        ) {
            ComboBox(
                isDarkTheme = isDarkTheme,
                items = items,
                label = label,
                field = field,
                enabled = true,
                onSelectedItem = { _ ->},
                modifier = Modifier
            )
        }

    }

}