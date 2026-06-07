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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.di.Modules.appModule
import com.budoxr.ett.presentation.domain.BarChartItemModel
import com.budoxr.ett.presentation.presenters.WeeklyBarChartScreenUiState
import com.budoxr.ett.presentation.presenters.WeeklyBarChartViewModel
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.components.PureBarChart
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.budoxr.ett.ui.theme.gray
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import timber.log.Timber

@Composable
fun WeeklyBarChartScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    viewModel: WeeklyBarChartViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    val weeklyBarChartScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = weeklyBarChartScreenUiState) {
        is WeeklyBarChartScreenUiState.Loading -> {
            WeeklyBarChartScreenLoading()
        }
        is WeeklyBarChartScreenUiState.Error -> {
            WeeklyBarChartScreenError(
                msg =  uiState.errorMessage!!,
                onRetry = { onBackButtonClick.invoke() }
            )
        }
        is WeeklyBarChartScreenUiState.Ready -> {
            WeeklyBarChartScreenReady(
                navController = navController,
                isDarkTheme = isDarkTheme,
                uiState = uiState,
                onBackButtonClick = onBackButtonClick,
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
    uiState: WeeklyBarChartScreenUiState.Ready,
    onBackButtonClick: onDismissType
) {
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)

    val filteredItems = remember(uiState.items) {
        uiState.items.filter { it.value != 0.0f }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Screens.WeeklyBarChartScreen.icon,
                titleIcon = null,
                title = stringResource(R.string.title_long_weekly_bar_chart_screen),
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
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalMargin),
                horizontalArrangement = Arrangement.Center
            ) {
                if (uiState.period != null) {
                    Text(
                        text = stringResource(R.string.title_date_period_format, uiState.period.first, uiState.period.second),
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
    val uiState = WeeklyBarChartScreenUiState.Ready(
        period = Pair("2023-01-01", "2023-01-07"),
        items = listOf(item)
    )

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface( modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
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
                )

            }
        }
    }
}

private const val TAG = "che.WeeklyBarChartScreen"
