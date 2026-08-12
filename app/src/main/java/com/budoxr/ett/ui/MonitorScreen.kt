/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.BuildConfig
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissComposableType
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.onLongType
import com.budoxr.ett.commons.onStringType
import com.budoxr.ett.commons.utils.TimeUtils.toTimestampFormat
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.data.adapters.TimerTrackingQueryAdapter
import com.budoxr.ett.data.adapters.TimersWithActivityAdapter
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.data.dummies.DummyRepository
import com.budoxr.ett.data.mapper.toModel
import com.budoxr.ett.presentation.domain.ActivityModel
import com.budoxr.ett.presentation.domain.TimerTrackingModel
import com.budoxr.ett.presentation.presenters.GroupedSumState
import com.budoxr.ett.presentation.presenters.MonitorFormState
import com.budoxr.ett.presentation.presenters.MonitorScreenUiState
import com.budoxr.ett.presentation.presenters.MonitorViewModel
import com.budoxr.ett.ui.components.ActivityFilterBottomSheet
import com.budoxr.ett.ui.components.ActivitySelectionBottomSheet
import com.budoxr.ett.ui.components.ConfirmDialog
import com.budoxr.ett.ui.components.DateRangePickerBottomSheet
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.components.MonitorScreenRowHistoricalItem
import com.budoxr.ett.ui.components.MonitorScreenRowItem
import com.budoxr.ett.ui.components.SearchField
import com.budoxr.ett.ui.components.SingleChoiceSegmentedButton
import com.budoxr.ett.ui.components.TimerTrackingBottomSheet
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


data class MonitorState(
    val navController: NavHostController,
    val isDarkTheme: Boolean,
    val isRefreshing: Boolean,
    val onRefresh: onDismissType,
    val onSearchChange: onStringType,
    val activeTimers: List<TimersWithActivity>,
    val historicalTimers: List<TimerTrackingQuery>,
    val onNewTimerClick: onLongType,
    val onStartClick: onLongType,
    val onStopClick: onLongType,
    val onDeleteClick: onLongType,
    val onHideTimer: onLongType,
    val onShowTimerTrackingBottomSheet: onDismissType,
    val onSaveTimerTracking: (TimerTrackingModel) -> Unit,
    val onActivityClick: (ActivityModel) -> Unit,
    val onBackButtonClick: onDismissType,
    val onSelectedView: onIntType,
    val onActivityFilterClick: onDismissType,
    val onDateRangeFilterClick: onDismissType,
    val onExportCsvClick: onDismissType,
    val onClearFiltersClick: onDismissType,
)

@Composable
fun MonitorScreen(
    isDarkTheme: Boolean,
    navController: NavHostController,
    onBackButtonClick: onDismissType,
    navigateToCsvReportScreen: onDismissType,
    viewModel: MonitorViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val monitorScreenUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val uiState = monitorScreenUiState) {
        is MonitorScreenUiState.Loading -> {
            MonitorScreenLoading()
        }
        is MonitorScreenUiState.Error -> {
            MonitorScreenError(
                msg = uiState.errorMessage!!,
                onRetry = { onBackButtonClick.invoke() }
            )
        }
        is MonitorScreenUiState.Ready -> {
            val monitorFormState by viewModel.formState.collectAsStateWithLifecycle()
            val floatingActionButton: onDismissComposableType = {
                FloatingActionButton(
                    onClick = viewModel::showActivityBottomSheet,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.content_description_icon)
                    )
                }
            }

            val noneFloatingActionButton: onDismissComposableType = {}

            if (BuildConfig.SAVE_DATA_TO_JSON) {
                Timber.tag(TAG).d("save \'timers\' to Json")
                uiState.historicalTimers
                viewModel.exportToJson(
                    activeTimer = uiState.activeTimers,
                    historicalTimer = uiState.historicalTimers
                )
            }

            MonitorScreenReady(
                navController = navController,
                uiState = uiState.copy(monitorFormState = monitorFormState),
                timerTrackingSelected = viewModel.timerTrackingSelected,
                isDarkTheme = isDarkTheme,
                isRefreshing = isRefreshing,
                onRefresh = viewModel::refresh,
                onSearchChange = viewModel::onSearchChange,
                onNewTimerClick = viewModel::newTimer,
                onStartClick = viewModel::startTimer,
                onStopClick = viewModel::stopTimer,
                onDeleteClick = viewModel::deleteTimer,
                onHideTimer = viewModel::showConfirmDialog,
                onShowTimerTrackingBottomSheet = viewModel::showTimerTrackingBottomSheet,
                onSaveTimerTracking = viewModel::saveTimerTracking,
                onActivityClick = viewModel::showActivitiesInTimerTrackingBottomSheet,
                onBackButtonClick = onBackButtonClick,
                onSelectedView = viewModel::changeView,
                onDismissBottomSheet = viewModel::dismissBottomSheet,
                onActivityFilterClick = viewModel::toggleActivityFilterBottomSheet,
                onDateRangeFilterClick = viewModel::toggleDateRangePickerBottomSheet,
                onUpdateFilterActivities = viewModel::updateFilterActivities,
                onUpdateFilterDateRange = viewModel::updateFilterDateRange,
                onClearFiltersClick = viewModel::clearFilters,
                floatingActionButton = if (uiState.selectedView == 0) floatingActionButton else noneFloatingActionButton,
                formatLastThreeDigits = viewModel::formatLastThreeDigits,
                navigateToCsvReportScreen = navigateToCsvReportScreen
            )

        }
    }

}

@Composable
private fun MonitorScreenLoading(modifier: Modifier = Modifier) {
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
private fun MonitorScreenError(msg: String?, onRetry: onDismissType?, modifier: Modifier = Modifier) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitorScreenReady(
    navController: NavHostController,
    uiState: MonitorScreenUiState.Ready,
    timerTrackingSelected: TimerTrackingModel?,
    isDarkTheme: Boolean,
    isRefreshing: Boolean,
    onRefresh: onDismissType,
    onSearchChange: onStringType,
    onNewTimerClick: onLongType,
    onStartClick: onLongType,
    onStopClick: onLongType,
    onDeleteClick: onLongType,
    onHideTimer: onLongType,
    onShowTimerTrackingBottomSheet: onDismissType,
    onSaveTimerTracking: (TimerTrackingModel) -> Unit,
    onActivityClick: (ActivityModel) -> Unit,
    onBackButtonClick: onDismissType,
    onSelectedView: onIntType,
    onDismissBottomSheet: onDismissType,
    onActivityFilterClick: onDismissType,
    onDateRangeFilterClick: onDismissType,
    onUpdateFilterActivities: (Set<Long>) -> Unit,
    onUpdateFilterDateRange: (Pair<String, String>) -> Unit,
    onClearFiltersClick: onDismissType,
    floatingActionButton: onDismissComposableType,
    formatLastThreeDigits: (Long) -> String,
    navigateToCsvReportScreen: onDismissType
) {

    val monitorState = MonitorState(
        navController = navController,
        isDarkTheme = isDarkTheme,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        onSearchChange = onSearchChange,
        activeTimers = uiState.activeTimers,
        historicalTimers = uiState.historicalTimers,
        onNewTimerClick = onNewTimerClick,
        onStartClick = onStartClick,
        onStopClick = onStopClick,
        onDeleteClick = onDeleteClick,
        onHideTimer = onHideTimer,
        onShowTimerTrackingBottomSheet = onShowTimerTrackingBottomSheet,
        onSaveTimerTracking = onSaveTimerTracking,
        onActivityClick = onActivityClick,
        onBackButtonClick = onBackButtonClick,
        onSelectedView = onSelectedView,
        onActivityFilterClick = onActivityFilterClick,
        onDateRangeFilterClick = onDateRangeFilterClick,
        onExportCsvClick = navigateToCsvReportScreen,
        onClearFiltersClick = onClearFiltersClick,
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val viewOptions: Array<String> = stringArrayResource(id = R.array.monitor_views_array)

    Scaffold(
        topBar = {
            GlobalTopBar(
                isDarkTheme = monitorState.isDarkTheme,
                navIcon = Screens.MonitorScreen.icon,
                onBackButtonClick = monitorState.onBackButtonClick,
                titleIcon = null,
                title = stringResource(Screens.MonitorScreen.titleResId),
                actionIcon = null,
                onActionButtonClick = {},
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = stringResource(R.string.label_select_activities)
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.label_activity)) },
                                onClick = {
                                    showMenu = false
                                    monitorState.onActivityFilterClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.label_select_date_range)) },
                                onClick = {
                                    showMenu = false
                                    monitorState.onDateRangeFilterClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.label_export_csv)) },
                                onClick = {
                                    showMenu = false
                                    monitorState.onExportCsvClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.label_clear_all)) },
                                onClick = {
                                    showMenu = false
                                    monitorState.onClearFiltersClick()
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { MainBottomBar(monitorState.navController) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = floatingActionButton
    ) { innerPadding ->
        val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)
        val lineSpacing1x = dimensionResource(id = R.dimen.line_spacing_1)

        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = horizontalMargin)
                    .padding(top = 12.dp)
            ) {
                SearchField(
                    value = uiState.monitorFormState!!.search,
                    onValueChange = monitorState.onSearchChange,
                    placeholder = stringResource(R.string.label_search_field_placeholder),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(vertical = lineSpacing1x))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SingleChoiceSegmentedButton(
                        modifier = Modifier,
                        options = viewOptions.toList(),
                        onChangeSelection = monitorState.onSelectedView,
                        selectedOptionIndex = uiState.selectedView
                    )
                }
            }

            if (uiState.selectedView == 0) {
                MonitorScreenContent(
                    modifier = Modifier.weight(1f),
                    monitorState = monitorState,
                    formatLastThreeDigits = formatLastThreeDigits
                )
            } else {
                MonitorScreenHistoricalContent(
                    modifier = Modifier.weight(1f),
                    monitorState = monitorState,
                    search = uiState.monitorFormState!!.search,
                    formatLastThreeDigits = formatLastThreeDigits
                )
            }
        }

        if (uiState.bottomSheetHandle.bottomSheetExpanded) {
            if (uiState.bottomSheetHandle.showActivity) {
                ActivitySelectionBottomSheet(
                    sheetState = sheetState,
                    // Function to handle dismissal (swipes, back button, or manual)
                    onDismiss = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissBottomSheet.invoke()
                            }
                        }
                    },
                    placeholderActivities = if (uiState.monitorFormState!!.search.isNotEmpty()) uiState.activities.filter {
                        it.name.contains(
                            uiState.monitorFormState.search,
                            ignoreCase = true
                        )
                    } else uiState.activities,
                    onActivitySelected = { id ->
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissBottomSheet.invoke()
                            }
                        }
                        monitorState.onNewTimerClick.invoke(id)
                    },
                    search = uiState.monitorFormState.search,
                    onSearchChange = monitorState.onSearchChange
                )
            } else if (uiState.bottomSheetHandle.showConfirmDialog) {
                ConfirmDialog(
                    title = stringResource(R.string.title_modify_record),
                    message = stringResource(R.string.msg_modify_record),
                    labelButtonConfirm = stringResource(R.string.label_confirm_button_yes),
                    labelButtonDismiss = stringResource(R.string.label_confirm_button_no),
                    onDismiss = {
                        val newTimerTracking = timerTrackingSelected!!.copy(
                            visible = false
                        )
                        monitorState.onSaveTimerTracking.invoke(newTimerTracking)
                        onDismissBottomSheet.invoke()
                    },
                    onConfirm = onShowTimerTrackingBottomSheet
                )
            } else if (uiState.bottomSheetHandle.showActivityFilter) {
                ActivityFilterBottomSheet(
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissBottomSheet.invoke()
                            }
                        }
                    },
                    activities = uiState.activities,
                    selectedActivities = uiState.monitorFormState?.filterSelectedActivities ?: emptySet(),
                    onActivitiesSelected = onUpdateFilterActivities,
                    search = uiState.monitorFormState?.search ?: "",
                    onSearchChange = monitorState.onSearchChange
                )
            } else if (uiState.bottomSheetHandle.showDateRangePicker) {
                DateRangePickerBottomSheet(
                    onDismiss = {
                        onDismissBottomSheet.invoke()
                    },
                    onRangeSelected = onUpdateFilterDateRange
                )
            } else {
                TimerTrackingBottomSheet(
                    sheetState = sheetState,
                    onDismiss = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissBottomSheet.invoke()
                            }
                        }
                    },
                    timerTrackingSelected = timerTrackingSelected!!,
                    showActivityList = uiState.bottomSheetHandle.showActivitiesInTimerTracking,
                    activities = uiState.activities.map { it.toModel() },
                    onClick = monitorState.onSaveTimerTracking,
                    onActivityClick = monitorState.onActivityClick
                )
            }
        }
    }
    
}

@Composable
fun MonitorScreenContent(
    modifier: Modifier,
    monitorState: MonitorState,
    formatLastThreeDigits: (Long) -> String,
) {
    val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)

    val pullToRefreshState = rememberPullToRefreshState()
    
    PullToRefreshBox(
        state = pullToRefreshState,          // Le pasa el estado del gesto
        isRefreshing = monitorState.isRefreshing,         // Estado actual (determina si el indicador está visible)
        onRefresh = monitorState.onRefresh,               // Función a llamar cuando el refresh es activado
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = horizontalMargin, end = horizontalMargin, top = 12.dp, bottom = 56.dp ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(monitorState.activeTimers) { timer ->
                MonitorScreenRowItem(
                    monitorState = monitorState,
                    nameActivity = timer.activity.name,
                    item = timer.timerTracking,
                    formatLastThreeDigits = formatLastThreeDigits,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                if (monitorState.activeTimers.isEmpty()) {
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

                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(R.string.msg_no_records), style = MaterialTheme.typography.titleMedium)
                    }
                }

            }

        }
    }
    
}


@Composable
fun MonitorScreenHistoricalContent(
    modifier: Modifier,
    monitorState: MonitorState,
    search: String,
    formatLastThreeDigits: (Long) -> String
) {
    val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)
    val lineSpacing1x = dimensionResource(id = R.dimen.line_spacing_1)
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)

    val pullToRefreshState = rememberPullToRefreshState()

    val grouped = remember(monitorState.historicalTimers) {
        monitorState.historicalTimers.groupBy { it.nameActivity }
            .map { (nameActivity, timers) ->
                val totalSum = timers.sumOf { it.timerTracking.elapsedTime }
                GroupedSumState(
                    groupKey = nameActivity,
                    items = timers,
                    totalSum = totalSum
                )
            }
    }

    PullToRefreshBox(
        state = pullToRefreshState,          // Le pasa el estado del gesto
        isRefreshing = monitorState.isRefreshing,         // Estado actual (determina si el indicador está visible)
        onRefresh = monitorState.onRefresh,               // Función a llamar cuando el refresh es activado
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = horizontalMargin, end = horizontalMargin, top = 12.dp, bottom = 56.dp ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val groupingFilter = grouped.filter { it.groupKey.contains(search, ignoreCase = true) }
            groupingFilter.forEach { groupData ->
                stickyHeader {
                    Text(
                        text = groupData.groupKey,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = horizontalMargin, vertical = lineSpacing1x)
                    )
                }

                items(
                    items = groupData.items,
                    key = { "item_${groupData.groupKey}_${it.timerTracking.timerTrackingId}"}
                ) { timer ->
                    MonitorScreenRowHistoricalItem(
                        monitorState = monitorState,
                        item = timer,
                        formatLastThreeDigits = formatLastThreeDigits,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Dynamically append the sum directly after the last element of this group
                item("sum_${groupData.groupKey}") {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(0.3f)
                    )
                    Text(
                        text = "Total elapsed time: ${groupData.totalSum.toTimestampFormat()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = horizontalMargin, top = lineSpacing1x, bottom = lineSpacing1x)
                    )

                }
            }


            item {
                if (monitorState.activeTimers.isEmpty()) {
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

                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(R.string.msg_no_records), style = MaterialTheme.typography.titleMedium)
                    }
                }

            }

        }
    }

}


@Composable
@Preview(showBackground = true)
fun MonitorScreenContentPreview() {
    val navController = rememberNavController()
    val isDarkTheme = false
    val context = LocalContext.current
    val utility =  Utility(context)
    val moshi = Moshi.Builder().build()
    val dummyRepository = DummyRepository(
        timersWithActivityAdapter = TimersWithActivityAdapter(moshi),
        timerTrackingQueryAdapter = TimerTrackingQueryAdapter(moshi)
    )
    val activeTimers = dummyRepository.allTimersWithActivity()
    val historicalTimers = dummyRepository.allTimerTrackingQuery()

    val selectedView = 1 // Historical
    val search = ""

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {

        MonitorScreenReady(
            navController = navController,
            uiState = MonitorScreenUiState.Ready(
                monitorFormState = MonitorFormState(search = search),
                activities = emptyList(),
                activeTimers = activeTimers,
                historicalTimers = historicalTimers,
                selectedView = selectedView,
                bottomSheetHandle = MonitorViewModel.BottomSheetHandle()
            ),
            timerTrackingSelected = null,
            isDarkTheme = isDarkTheme,
            isRefreshing = false,
            onRefresh = {},
            onSearchChange = {},
            onNewTimerClick = {},
            onStartClick = {},
            onStopClick = {},
            onDeleteClick = {},
            onHideTimer = {},
            onShowTimerTrackingBottomSheet = {},
            onSaveTimerTracking = {},
            onActivityClick = {},
            onBackButtonClick = {},
            onSelectedView = {},
            onDismissBottomSheet = {},
            onActivityFilterClick = {},
            onDateRangeFilterClick = {},
            onUpdateFilterActivities = {},
            onUpdateFilterDateRange = {},
            onClearFiltersClick = {},
            floatingActionButton = {},
            formatLastThreeDigits = utility::formatLastThreeDigits,
            navigateToCsvReportScreen = {}
        )

    }


}


private const val TAG = "che.MonitorScreen"