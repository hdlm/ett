package com.budoxr.ett.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.onLongType
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.presentation.presenters.MonitorScreenUiState
import com.budoxr.ett.presentation.presenters.MonitorViewModel
import com.budoxr.ett.ui.components.ActivitySelectionBottomSheet
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.components.SingleChoiceSegmentedButton
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


data class MonitorState(
    val navController: NavHostController,
    val isDarkTheme: Boolean,
    val isRefreshing: Boolean,
    val onRefresh: onDismissType,
    val timers: List<TimersWithActivity>,
    val onNewTimerClick: onIntType,
    val onStartClick: onLongType,
    val onStopClick: onLongType,
    val onDoneTimer: onLongType,
    val onBackButtonClick: onDismissType,
    val onSelectedFilter: onIntType,
)

@Composable
fun MonitorScreen(
    isDarkTheme: Boolean,
    navController: NavHostController,
    onBackButtonClick: onDismissType,
    viewModel: MonitorViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    //TODO save the current screen into the session object

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
            MonitorScreenReady(
                navController = navController,
                uiState = uiState,
                isDarkTheme = isDarkTheme,
                isRefreshing = isRefreshing,
                onRefresh = viewModel::refresh,
                onNewTimerClick = viewModel::newTimer,
                onStartClick = viewModel::startTimer,
                onStopClick = viewModel::stopTimer,
                onDoneTimer = viewModel::doneTimer,
                onBackButtonClick = onBackButtonClick,
                onSelectedFilter = viewModel::onChangeFilter,
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
    isDarkTheme: Boolean,
    isRefreshing: Boolean,
    onRefresh: onDismissType,
    onNewTimerClick: onIntType,
    onStartClick: onLongType,
    onStopClick: onLongType,
    onDoneTimer: onLongType,
    onBackButtonClick: onDismissType,
    onSelectedFilter: onIntType,
) {

    val monitorState = MonitorState(
        navController = navController,
        isDarkTheme = isDarkTheme,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        timers = uiState.activeTimers,
        onNewTimerClick = onNewTimerClick,
        onStartClick = onStartClick,
        onStopClick = onStopClick,
        onDoneTimer = onDoneTimer,
        onBackButtonClick = onBackButtonClick,
        onSelectedFilter = onSelectedFilter,
    )

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            GlobalTopBar(
                isDarkTheme = monitorState.isDarkTheme,
                navIcon = Screens.MonitorScreen.icon,
                onBackButtonClick = monitorState.onBackButtonClick,
                titleIcon = null,
                title = stringResource(Screens.MonitorScreen.titleResId),
                actionIcon = null,
                onActionButtonClick = {},
            )
        },
        bottomBar = { MainBottomBar(monitorState.navController) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton =  {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
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
        
        MonitorScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            monitorState = monitorState,
        )

        if (showBottomSheet) {
            ActivitySelectionBottomSheet(
                sheetState = sheetState,
                // Function to handle dismissal (swipes, back button, or manual)
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                onActivitySelected = { activityId ->
                    // 4. Handle selection, then dismiss
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                    // TODO: Replace with logic to start timer with the selected Activity ID.
                    // For now, we call the existing FAB action:
//                    monitorState.onNewTimerClick.invoke()
                }
            )
        }
         
    }
    
}

@Composable
fun MonitorScreenContent(
    modifier: Modifier,
    monitorState: MonitorState,
) {
    val horizontalMargin = dimensionResource(id = R.dimen.margin_horizontal)
    val iconSize = dimensionResource(id = R.dimen.icon_huge_size)

    val pullToRefreshState = rememberPullToRefreshState()
    
    val viewsArray: Array<String> = stringArrayResource(id = R.array.monitor_views_array)

    PullToRefreshBox(
        state = pullToRefreshState,          // Le pasa el estado del gesto
        isRefreshing = monitorState.isRefreshing,         // Estado actual (determina si el indicador está visible)
        onRefresh = monitorState.onRefresh,               // Función a llamar cuando el refresh es activado
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SingleChoiceSegmentedButton(
                        modifier = Modifier,
                        options = viewsArray.toList(),
                        onChangeSelection =  monitorState.onSelectedFilter,
                    )
                }
            }

            items(monitorState.timers) { timers ->
                MonitorScreenRowItem(
                    monitorState = monitorState,
                    item = timers.timerTracking,
                    modifier = Modifier
                )
            }

            item {

                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                        contentDescription = stringResource(R.string.content_description_icon),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant, //standard icon color
                        modifier = Modifier.size(iconSize)
                    )
                }

                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (monitorState.timers.isEmpty()) {
                        Text(text = stringResource(R.string.msg_no_records), style = MaterialTheme.typography.titleMedium)
                    }
                }

            }

        }
    }
    
}


@Composable
fun MonitorScreenRowItem(
    monitorState: MonitorState,
    item: TimerTrackingEntity,
    modifier: Modifier
) {

    val iconSize = dimensionResource(id = R.dimen.icon_tiny_size)
    val playIcon = Icons.Filled.PlayCircle
    val stopIcon = Icons.Filled.StopCircle

    var checked by remember { mutableStateOf(item.done) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "(nro)", style = MaterialTheme.typography.bodySmall)
        Text(text = "name actv", style = MaterialTheme.typography.bodySmall)
        Text(text = "${item.elapsedTime}", style = MaterialTheme.typography.bodySmall)

        if (!item.done) {
            IconButton(
                onClick = { monitorState.onStartClick.invoke(item.timerTrackingId!!)},
                enabled = true
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = playIcon,
                    contentDescription = stringResource(id = R.string.content_description_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant, //standard icon color
                )
            }
        } else {
            IconButton(
                onClick = { monitorState.onStopClick.invoke(item.timerTrackingId!!)},
                enabled = true
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = stopIcon,
                    contentDescription = stringResource(id = R.string.content_description_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant, //standard icon color
                )
            }
        }

        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                monitorState.onDoneTimer.invoke(item.timerTrackingId!!)
            }
        )

    }

}


@Composable
@Preview(showBackground = true)
fun MonitorScreenContentPreview() {
    val navController = rememberNavController()
    val isDarkTheme = false

    val timer1 = TimerTrackingEntity(
        timerTrackingId = 1,
        startTime = "02:00:00",
        endTime = "02:10:00",
        elapsedTime = 10L,
        visible = true,
        done = false,
        activityId = 1,
    )
    val timer2 = TimerTrackingEntity(
        timerTrackingId = 2,
        startTime = "02:20:00",
        endTime = "02:50:00",
        elapsedTime = 30L,
        visible = true,
        done = false,
        activityId = 2,
    )
    val timers = listOf(timer1, timer2)

    val monitorState = MonitorState(
        navController = navController,
        isDarkTheme = isDarkTheme,
        isRefreshing = false,
        onRefresh = {},
        onNewTimerClick = {},
        timers = emptyList(),
        onStartClick = {_ ->},
        onStopClick = {_ ->},
        onDoneTimer = {_ ->},
        onBackButtonClick = {},
        onSelectedFilter = {_ ->},
    )

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        MonitorScreenContent(
            modifier = Modifier.fillMaxSize(),
            monitorState = monitorState,
        )
    }


}


private const val TAG = "che.MonitorScreen"