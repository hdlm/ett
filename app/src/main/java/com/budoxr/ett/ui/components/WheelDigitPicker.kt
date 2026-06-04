package io.dynamiteapps.dribbli.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelDigitPicker(
    modifier: Modifier = Modifier,
    selectedValue: Int,
    maxRange: Int = 99,
    itemHeight: Dp = 45.dp,
    onValueChange: onIntType
) {
    val totalItems = 10000 // Large number to simulate infinite loop
    val midOffset = totalItems / 2

    // Calculate the start index so it centers precisely on the selectedValue
    val startIndex = remember(selectedValue) {
        val currentRangeOffset = midOffset % (maxRange + 1)
        midOffset - currentRangeOffset + selectedValue
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex - 1)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Derive the currently centered item index safely
    val centeredItemIndex = remember {
        derivedStateOf { listState.firstVisibleItemIndex + 1 }
    }

    // Emit state changes back up to the parent when snapping finishes
    LaunchedEffect(centeredItemIndex.value) {
        snapshotFlow { centeredItemIndex.value }
            .distinctUntilChanged()
            .collect { index ->
                val actualValue = index % (maxRange + 1)
                if (actualValue != selectedValue) {
                    onValueChange(actualValue)
                }
            }
    }

    // A viewport showing 3 items at a time (1 center active, 2 semi-hidden)
    Box(
        modifier = modifier.height(itemHeight * 3),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(totalItems) { index ->
                val digit = index % (maxRange + 1)
                val isSelected = index == centeredItemIndex.value

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format("%02d", digit),
                        fontSize = if (isSelected) 26.sp else 20.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun WheelDigitPicker() {
    val isDarkTheme = false

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            WheelDigitPicker(
                selectedValue = 12,
                onValueChange = {}
            )
        }
    }
}