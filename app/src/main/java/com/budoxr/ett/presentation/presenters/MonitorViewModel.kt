/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import android.database.sqlite.SQLiteException
import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.toFechaTimeDb
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.commons.utils.toTimestamp
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.data.datastore.repositories.UserPreferencesRepository
import com.budoxr.ett.data.mapper.toEntity
import com.budoxr.ett.data.mapper.toModel
import com.budoxr.ett.presentation.domain.TimerTrackingModel
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingDeleteUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingVisibleInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import com.budoxr.ett.ui.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class MonitorViewModel(
    private val timerTrackingInfoUseCase: TimerTrackingInfoUseCase,
    private val timerTrackingVisibleInfoUseCase: TimerTrackingVisibleInfoUseCase,
    private val timerTrackingInsertUseCase: TimerTrackingInsertUseCase,
    private val timerTrackingDeleteUseCase: TimerTrackingDeleteUseCase,
    private val activityInfoUseCase: ActivityInfoUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val utility: Utility
) : KoinViewModel() {

    private val _activities = activityInfoUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(CommonValues.WHILE_SUBSCRIBED),
        initialValue = emptyList()
    )

    private val _timers = MutableStateFlow<List<TimersWithActivity>>(emptyList())
    private val _historical = MutableStateFlow<List<TimerTrackingQuery>>(emptyList())
    private val _selectedView = MutableStateFlow(0)
    private val _bottomSheetHandle = MutableStateFlow(BottomSheetHandle())

    private var _timerTrackingSelected: TimerTrackingModel? = null
    val timerTrackingSelected: TimerTrackingModel?
        get() = _timerTrackingSelected

    private val _uiState = MutableStateFlow<MonitorScreenUiState>(MonitorScreenUiState.Loading)
    val uiState: StateFlow<MonitorScreenUiState>
        get() = _uiState.asStateFlow()


    private val refreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = refreshing.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                _activities,
                _timers.flatMapLatest { _ ->
                    timerTrackingVisibleInfoUseCase.invoke()
                },
                _historical.flatMapLatest { _ ->
                    timerTrackingInfoUseCase.invoke()
                },
                _selectedView,
                _bottomSheetHandle,
                refreshing
            ) { activities,
                timers,
                historical,
                selectedView,
                bottomSheetHandle,
                refreshing ->

                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine MonitorScreenUiState.Loading
                }

                saveLastScreen()

                _timers.value = timers
                _historical.value = historical

                // get only the activities that are not active
                val activeActivityIds = timers
                    .filter { !it.timerTracking.done }
                    .map { it.activity.activityId }
                    .toSet()
                val availableActivities = activities.filter { it.activityId !in activeActivityIds }

                MonitorScreenUiState.Ready(
                    activities = availableActivities,
                    activeTimers = timers,
                    historicalTimers = historical,
                    selectedView = selectedView,
                    bottomSheetHandle = bottomSheetHandle
                )

            }.catch { throwable ->
                throwable.printStackTrace()
                _uiState.value = MonitorScreenUiState.Error( errorMessage = "error: ${throwable.message}" )
                Timber.tag(TAG).e( throwable,"error: ${throwable.localizedMessage}")
            }.collect {
                _uiState.value = it
            }
        }
        refresh(force = true)

    }


    fun refresh(force: Boolean = true ) {
        Timber.tag(TAG).i("refresh() -> invoked, force: $force")
        viewModelScope.launch {
            runCatching {
                refreshing.update { force }
                if(force) {
                    delay(CommonValues.MIN_WAIT)
                    refreshing.update { false }
                }
            }
        }
    }

    fun emptyTimer(activityId: Long) = TimerTrackingEntity(
        timerTrackingId = null,
        startTime = Date().toFechaTimeDb(),
        endTime = null,
        activityId = activityId,
    )

    fun newTimer(activityId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.tag(TAG).d("newTimer() -> called, index: $activityId")
            try  {
                val activity = _activities.value.find { it.activityId == activityId }
                activity?.let {
                    val timerTracking = TimerTrackingEntity(
                        startTime = TimeUtils.toTimestamp(),
                        visible = true,
                        done = false,
                        activityId = it.activityId!!
                    )

                    val id = timerTrackingInsertUseCase.invoke(timerTracking)
                    Timber.tag(TAG).d("newTimer() -> new register added with id: $id")
                    startTimer(id)
                }

            } catch (e: SQLiteException) {
                Timber.tag(TAG).e(e, "Error creating new timer")
                _uiState.update { MonitorScreenUiState.Error( errorMessage = "error: ${e.message}" ) }
            }
        }

    }
    fun startTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("startTimer() -> called")
        // By default when it is created a new timer, it is start the timer

    }

    fun stopTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("stopTimer() -> called")

        val timerTracking = _timers.value.find { it.timerTracking.timerTrackingId == timeTrackingId }
        timerTracking?.let {
            val startTimestamp = it.timerTracking.startTime
            val endTimestamp = TimeUtils.toTimestamp()
            val timerTrackingUpdated = TimerTrackingEntity(
                timerTrackingId = it.timerTracking.timerTrackingId,
                startTime = startTimestamp,
                endTime = endTimestamp,
                elapsedTime = TimeUtils.calculateTimestampDifference(
                    startTimestamp = startTimestamp,
                    endTimestamp = endTimestamp
                ),
                visible = true,
                done = true,
                activityId = it.activity.activityId!!
            )
            viewModelScope.launch(Dispatchers.IO) {
                timerTrackingInsertUseCase.invoke(timerTrackingUpdated)
                Timber.tag(TAG).i("Timer updated with id: $timeTrackingId")
            }
        }

    }


    fun deleteTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("deleteTimer() -> called")

        val timerTracking = _historical.value.find { it.timerTracking.timerTrackingId == timeTrackingId }
        timerTracking?.let {
            viewModelScope.launch(Dispatchers.IO) {
                timerTrackingDeleteUseCase.invoke(it.timerTracking)
                Timber.tag(TAG).i("Timer deleted with id: $timeTrackingId")
            }
        }

    }

    fun changeView(index: Int) {
        Timber.tag(TAG).d("changeView() -> called, index: $index")
        _selectedView.update { index }
    }

    fun showActivityBottomSheet() {
        Timber.tag(TAG).d("showActivityBottomSheet() -> called")
        _bottomSheetHandle.update { _bottomSheetHandle.value.showActivityBottomSheet() }
    }

    fun dismissBottomSheet() {
        Timber.tag(TAG).d("onDismissBottomSheet() -> called")
        _bottomSheetHandle.update { _bottomSheetHandle.value.dismissAll() }
    }

    fun showTimerTrackingBottomSheet(timeTrackingId: Long) {
        Timber.tag(TAG).d("showTimerTracking() -> called, timerTrackingId: $timeTrackingId")

        val timerTrackingSelected = _timers.value.find { it.timerTracking.timerTrackingId == timeTrackingId }
        timerTrackingSelected?.let {
            _timerTrackingSelected = it.timerTracking.toModel()
        }
        _bottomSheetHandle.update { _bottomSheetHandle.value.showTimerTrackingBottomSheet() }
    }

    fun saveTimerTracking(timerTracking: TimerTrackingModel) {
        Timber.tag(TAG)
            .d("saveTimerTracking() -> called, timerTracking: ${timerTracking.timerTrackingId}")
        viewModelScope.launch(Dispatchers.IO) {
            timerTrackingInsertUseCase.invoke(timerTracking.toEntity())
        }

    }

    private fun saveLastScreen() {
        val baseRoute = Screens.MonitorScreen.baseRoute
        viewModelScope.launch {
            userPreferencesRepository.saveLastScreen(baseRoute)
            Timber.tag(TAG).i("save the last screen: $baseRoute")
        }
    }

    fun formatLastThreeDigits(value: Long): String = utility.formatLastThreeDigits(value)

    data class BottomSheetHandle(
        val bottomSheetExpanded: Boolean = false,
        val showActivity: Boolean = false,
        val showTimerTracking: Boolean = false,
    ) {
        fun showActivityBottomSheet(): BottomSheetHandle {
            val sheetHandle = reset()
            return sheetHandle.copy(
                showActivity = true,
                bottomSheetExpanded = true
            )

        }

        fun showTimerTrackingBottomSheet(): BottomSheetHandle {
            val sheetHandle = reset()
            return sheetHandle.copy(
                showTimerTracking = true,
                bottomSheetExpanded = true
            )

        }

        private fun reset(): BottomSheetHandle {
            return copy(
                bottomSheetExpanded = false,
                showActivity = false,
                showTimerTracking = false,
            )
        }

        fun dismissAll() = reset()
    }


    companion object {
        private const val TAG = "che.MonitorViewModel"
    }
}


sealed interface MonitorScreenUiState {
    data object Loading : MonitorScreenUiState

    data class Error(
        val errorMessage: String? = null
    ) : MonitorScreenUiState

    data class Ready(
        val activities: List<ActivityEntity> = emptyList(),
        val activeTimers: List<TimersWithActivity> = emptyList(),
        val historicalTimers: List<TimerTrackingQuery> = emptyList(),
        val selectedView: Int = 0,
        val bottomSheetHandle: MonitorViewModel.BottomSheetHandle = MonitorViewModel.BottomSheetHandle()
    ) : MonitorScreenUiState

}

data class GroupedSumState(
    val groupKey: String,
    val items: List<TimerTrackingQuery>,
    val totalSum: Long
)