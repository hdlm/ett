package com.budoxr.ett.ui.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import androidx.compose.material3.MaterialTheme
import com.budoxr.ett.commons.onIntType

@Composable
fun SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    options: List<String>,
    onChangeSelection: onIntType,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val segmentedButtonColors = SegmentedButtonDefaults.colors(
        activeContainerColor = MaterialTheme.colorScheme.primary,
        activeContentColor = MaterialTheme.colorScheme.onPrimary,
        inactiveContainerColor = MaterialTheme.colorScheme.surface,
        inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )


    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onChangeSelection(selectedIndex)
                },
                selected = index == selectedIndex,
                colors = segmentedButtonColors,
                label = { Text(label) }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SingleChoiceSegmentedButtonPreview() {
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        SingleChoiceSegmentedButton(
            modifier = Modifier,
            options = listOf("Currents", "Historical"),
            onChangeSelection = { _ ->},
        )

    }

}


