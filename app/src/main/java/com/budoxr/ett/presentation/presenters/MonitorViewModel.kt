package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.toFechaTimeDb
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.presentation.usecase.TimerTrackingActiveInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import timber.log.Timber
import java.util.Date

class MonitorViewModel : KoinViewModel() {
    private val timerTrackingActiveInfoUseCase: TimerTrackingActiveInfoUseCase by inject()
    private val timerTrackingInsertUseCase: TimerTrackingInsertUseCase by inject()

    private val _timers = MutableStateFlow<List<TimersWithActivity>>(emptyList())
    private val _selectedFilter = MutableStateFlow(0)

    private val _uiState = MutableStateFlow<MonitorScreenUiState>(MonitorScreenUiState.Loading)
    val uiState: StateFlow<MonitorScreenUiState>
        get() = _uiState.asStateFlow()


    private val refreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = refreshing.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                _timers.flatMapLatest { _ ->
                    timerTrackingActiveInfoUseCase.invoke()
                },
                _selectedFilter,
                refreshing,
            ) { timers,
                selectedFilter,
                refreshing ->

                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine MonitorScreenUiState.Loading
                }

                //TODO apply the filter to the list of timers

                MonitorScreenUiState.Ready(
                    activeTimers = timers,
                    selectedFilter = selectedFilter,
                )

            }.catch { throwable ->
                throwable.printStackTrace()
                _uiState.value = MonitorScreenUiState.Error( errorMessage = "error: ${throwable.message}" )
                Timber.tag(TAG).e("error: ${throwable.localizedMessage}", throwable)
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

    fun emptyTimer(activityId: Int) = TimerTrackingEntity(
        timerTrackingId = null,
        startTime = Date().toFechaTimeDb(),
        endTime = null,
        activityId = activityId,
    )

    fun newTimer(activityId: Int) {
        Timber.tag(TAG).d("newTimer() -> called, activityId: $activityId")
        //TODO create the new timer

    }
    fun startTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("startTimer() -> called")
        viewModelScope.launch(Dispatchers.IO) {
            //TODO save the time tracking to the database
        }

    }

    fun stopTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("stopTimer() -> called")
        //TODO implement the stop click

    }

    fun doneTimer(timeTrackingId: Long) {
        Timber.tag(TAG).d("doneTimer() -> called")
        //TODO not implemented yet
    }

    fun onChangeFilter(index: Int) {
        Timber.tag(TAG).d("onChangeFilter() -> called, index: $index")
        //TODO not implemented yet
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
        val activeTimers: List<TimersWithActivity> = emptyList(),
        val selectedFilter: Int = 0,
    ) : MonitorScreenUiState

}