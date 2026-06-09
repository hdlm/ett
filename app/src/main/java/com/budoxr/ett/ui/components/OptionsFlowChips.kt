package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budoxr.ett.R
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionsFlowChips(
    modifier: Modifier = Modifier,
    options: Array<String>,
    selectedOptionIndex: Int = 0,
    onChangeSelection: (Int) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { index, title ->
            FilterChip(
                selected = selectedOptionIndex == index,
                onClick = { onChangeSelection(index) },
                label = { Text(title) }
            )
        }
    }
}



@Composable
@Preview(showBackground = true)
private fun OptionsFlowChipsPreview() {
    val isDarkTheme = false
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)
    val filterOptions: Array<String> = stringArrayResource(id = R.array.filter_chart_array)

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()
                .padding(horizontalMargin),
            ) {
                OptionsFlowChips(
                    options = filterOptions,
                    selectedOptionIndex = 0,
                    onChangeSelection = {}
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

        }

    }
}