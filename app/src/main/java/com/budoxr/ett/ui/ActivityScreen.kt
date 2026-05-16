/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onLongType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.presentation.presenters.ActivityScreenUiState
import com.budoxr.ett.presentation.presenters.ActivityViewModel
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.components.SearchField
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

data class ActivityDataState(
    val navController: NavHostController,
    val isDarkTheme: Boolean,
    val activities: List<ActivityEntity>,
    val search: String,
    val isRefreshing: Boolean,
    val onRefresh: onDismissType,
    val onSearchChange: onStringType,
    val onNewActivityClick: onDismissType,
    val onEditActivityClick: onLongType,
    val onBackButtonClick: onDismissType,
)


@Composable
fun ActivityScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    navigateToActivityForm: onLongType,
    viewModel: ActivityViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val activityScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = activityScreenUiState) {
        is ActivityScreenUiState.Loading -> {
            ActivityScreenLoading()
        }
        is ActivityScreenUiState.Error -> {
            ActivityScreenError(
                msg = uiState.errorMessage!!,
                onRetry = { onBackButtonClick.invoke() }
            )
        }
        is ActivityScreenUiState.Ready -> {
            ActivityScreenReady(
                navController = navController,
                uiState = uiState,
                isDarkTheme = isDarkTheme,
                isRefreshing = isRefreshing,
                onRefresh = viewModel::refresh,
                onSearchChange = viewModel::onSearchChange,
                onNewActivityClick = { navigateToActivityForm.invoke(0L) },
                onEditActivityClick = navigateToActivityForm,
                onBackButtonClick = onBackButtonClick,
            )
        }
    }

}


@Composable
fun ActivityScreenLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun ActivityScreenError(
    modifier: Modifier = Modifier,
    msg: String?,
    onRetry: onDismissType? = null,
) {
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


@Composable
fun ActivityScreenReady(
    navController: NavHostController,
    uiState: ActivityScreenUiState.Ready,
    isDarkTheme: Boolean,
    isRefreshing: Boolean,
    onRefresh: onDismissType,
    onSearchChange: onStringType,
    onNewActivityClick: onDismissType,
    onEditActivityClick: onLongType,
    onBackButtonClick: onDismissType,
) {

    val activityDataState = ActivityDataState(
        navController = navController,
        isDarkTheme = isDarkTheme,
        activities = uiState.activities,
        search = uiState.activityState.search,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        onSearchChange = onSearchChange,
        onNewActivityClick = onNewActivityClick,
        onEditActivityClick = onEditActivityClick,
        onBackButtonClick = onBackButtonClick,
    )

    Scaffold (
        topBar = {
            GlobalTopBar(
                isDarkTheme = activityDataState.isDarkTheme,
                navIcon = Screens.ActivityScreen.icon,
                onBackButtonClick = activityDataState.onBackButtonClick,
                titleIcon = null,
                title = stringResource(Screens.ActivityScreen.titleResId),
                actionIcon = null,
                onActionButtonClick = {},
            )
        },
        bottomBar = { MainBottomBar(activityDataState.navController) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton =  {
            FloatingActionButton(
                onClick = {
                    activityDataState.onNewActivityClick.invoke()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.content_description_icon)
                )
            }
        },
    ) { innerPadding ->

        ActivityScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            activityDataState = activityDataState,
        )

    }

}


@Composable
fun ActivityScreenContent(
    modifier: Modifier,
    activityDataState: ActivityDataState,
) {
    val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)
    val lineSpacing1x = dimensionResource(id = R.dimen.line_spacing_1)
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)

    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,          // Le pasa el estado del gesto
        isRefreshing = activityDataState.isRefreshing,         // Estado actual (determina si el indicador está visible)
        onRefresh = activityDataState.onRefresh,               // Función a llamar cuando el refresh es activado
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SearchField(
                    value = activityDataState.search,
                    onValueChange = activityDataState.onSearchChange,
                    placeholder = stringResource(R.string.label_search_field_placeholder),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(vertical = lineSpacing1x))
            }

            items(activityDataState.activities) { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { activityDataState.onEditActivityClick(item.activityId!!) }
                        .padding(16.dp)
                )
            }

            item {
                if (activityDataState.activities.isEmpty()) {
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                            contentDescription = stringResource(R.string.content_description_icon),
                            tint = MaterialTheme.colorScheme.primary, //standard icon color (onSurfaceVariant)
                            modifier = Modifier.size(iconSize)
                        )
                    }

                    Text(text = stringResource(R.string.msg_no_records), style = MaterialTheme.typography.titleMedium)
                }
            }
        }

    }

}



@Composable
@Preview(showBackground = true)
fun ActivityScreenPreview() {
    val navController = rememberNavController()
    val isDarkTheme = false
    
    val actv1 = ActivityEntity(
        activityId = 1,
        name = "Activity One",
        color = Color(-34757177461702656L)
    )
    val actv2 = ActivityEntity(
        activityId = 2,
        name = "Activity Two",
        color = Color(-34757177461702656L)
    )
    val actv3 = ActivityEntity(
        activityId = 3,
        name = "Activity Three",
        color = Color(-34757177461702656L)
    )
    val activities = listOf(actv1, actv2, actv3)

    val activityDataState = ActivityDataState(
        navController = navController,
        isDarkTheme = isDarkTheme,
        activities = activities,
        search = "",
        isRefreshing = false,
        onRefresh = {},
        onSearchChange = { _ -> },
        onNewActivityClick = {},
        onEditActivityClick = { _ ->},
        onBackButtonClick = {}
    )

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ActivityScreenContent(
                modifier = Modifier,
                activityDataState = activityDataState
            )
        }
    }
    
}


private const val TAG = "che.ActivityScreen"
