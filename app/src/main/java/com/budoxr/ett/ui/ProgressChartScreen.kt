/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.presentation.domain.BarChartItemModel
import com.budoxr.ett.presentation.presenters.ProgressChartScreenUiState
import com.budoxr.ett.presentation.presenters.ProgressChartViewModel
import com.budoxr.ett.ui.components.DateRangePickerBottomSheet
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.components.OptionsFlowChips
import com.budoxr.ett.ui.components.PureBarChart
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.budoxr.ett.ui.theme.gray
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun ProgressChartScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    viewModel: ProgressChartViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    val weeklyBarChartScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = weeklyBarChartScreenUiState) {
        is ProgressChartScreenUiState.Loading -> {
            WeeklyBarChartScreenLoading()
        }
        is ProgressChartScreenUiState.Error -> {
            WeeklyBarChartScreenError(
                msg =  uiState.errorMessage!!,
                onRetry = { onBackButtonClick.invoke() }
            )
        }
        is ProgressChartScreenUiState.Ready -> {
            val onChangePeriod: onIntType = {
                viewModel.changePeriod(ProgressChartViewModel.DatePeriod.fromIndex(it))
            }

            WeeklyBarChartScreenReady(
                navController = navController,
                isDarkTheme = isDarkTheme,
                uiState = uiState,
                onBackButtonClick = onBackButtonClick,
                onChangePeriod = onChangePeriod,
                onChangeDateRange = viewModel::changeDateRange,
                onDismiss = viewModel::dismiss
            )

        }
    }

}


@Composable
private fun WeeklyBarChartScreenLoading(modifier: Modifier = Modifier) {
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)
    val areaSize = 94.dp

    Surface(modifier.fillMaxSize()) {
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(areaSize)
                    .align(Alignment.Center),
                strokeWidth = 8.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Image( modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape)
                .size(iconSize),
                painter = painterResource(id = R.drawable.ett_logo),
                contentDescription = stringResource(id = R.string.content_description_ett_logo),
                contentScale = ContentScale.Fit,
            )
        }
    }
}


@Composable
private fun WeeklyBarChartScreenError(msg: String?, onRetry: onDismissType?, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.content_description_icon),
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = msg ?: stringResource(R.string.msg_an_error_has_occurred),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            onRetry?.let {
                Button(onClick = it) {
                    Text(text = stringResource(R.string.label_retry))
                }
            }
        }

    }
}


@Composable
fun WeeklyBarChartScreenReady(
    navController: NavHostController,
    isDarkTheme: Boolean,
    uiState: ProgressChartScreenUiState.Ready,
    onBackButtonClick: onDismissType,
    onChangePeriod: onIntType,
    onChangeDateRange: (Pair<String,String>) -> Unit,
    onDismiss: onDismissType
) {
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)
    val filterOptions: Array<String> = stringArrayResource(id = R.array.filter_chart_array)

    val filteredItems = remember(uiState.items) {
        uiState.items.filter { it.value != 0.0f }
    }

    Scaffold(
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Screens.ProgressChartScreen.icon,
                titleIcon = null,
                title = stringResource(R.string.title_long_progress_bar_chart_screen),
                actionIcon = null,
                onBackButtonClick = onBackButtonClick,
                navIconPainter = null,
                onActionButtonClick = {}
            )
        },
        bottomBar = { MainBottomBar(navController) }
    ) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
        ) {

            OptionsFlowChips(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = horizontalMargin),
                options = filterOptions,
                selectedOptionIndex = uiState.period?.ordinal ?: 0,
                onChangeSelection = onChangePeriod
            )

            Row( modifier = Modifier.fillMaxWidth()
                .padding(horizontalMargin),
                horizontalArrangement = Arrangement.Center
            ) {
                if (uiState.labelPeriod != null) {
                    Text(
                        text = stringResource(R.string.title_date_period_format, uiState.labelPeriod.first, uiState.labelPeriod.second),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PureBarChart(
                    items = filteredItems,
                    modifier = Modifier.padding(horizontalMargin)
                )
            }

            if (uiState.bottomSheetHandle.bottomSheetExpanded) {
                if (uiState.bottomSheetHandle.showDateRangePicker) {
                    DateRangePickerBottomSheet(
                        onDismiss = onDismiss,
                        onRangeSelected = onChangeDateRange
                    )
                }
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun WeeklyBarChartScreenPreview() {
    val navController = rememberNavController()
    val item = BarChartItemModel(
        key = 1,
        value = 3600.00F,
        label = "C++",
        color = gray
    )
    val isDarkTheme = false
    val uiState = ProgressChartScreenUiState.Ready(
        labelPeriod = Pair("2023-01-01", "2023-01-07"),
        items = listOf(item)
    )

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface( modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(
                    text = stringResource(R.string.title_date_period_format, "2023-01-01", "2023-01-07"),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                WeeklyBarChartScreenReady(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    uiState = uiState,
                    onBackButtonClick = {},
                    onChangePeriod = { _ -> },
                    onChangeDateRange = { _ -> },
                    onDismiss = {}
                )

            }
        }
    }
}

private const val TAG = "che.WeeklyBarChartScreen"
