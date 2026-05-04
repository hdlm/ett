package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.toFechaTimeDb
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.commons.utils.toTimestamp
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingActiveInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
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
    private val timerTrackingActiveInfoUseCase: TimerTrackingActiveInfoUseCase,
    private val timerTrackingInsertUseCase: TimerTrackingInsertUseCase,
    private val activityInfoUseCase: ActivityInfoUseCase,
) : KoinViewModel() {

    private val _activities = activityInfoUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(CommonValues.WHILE_SUBSCRIBED),
        initialValue = emptyList()
    )


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
                _activities,
                _timers.flatMapLatest { _ ->
                    timerTrackingActiveInfoUseCase.invoke()
                },
                _selectedFilter,
                refreshing,
            ) { activities,
                timers,
                selectedFilter,
                refreshing ->

                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine MonitorScreenUiState.Loading
                }

                //TODO apply the filter to the list of timers

                MonitorScreenUiState.Ready(
                    activities = activities.toList(),
                    activeTimers = timers,
                    selectedFilter = selectedFilter,
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

    fun newTimer(index: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.tag(TAG).d("newTimer() -> called, index: $index")
            try  {
                val activity = _activities.value.elementAt(index.toInt())

                val timerTracking = TimerTrackingEntity(
                    startTime = TimeUtils.toTimestamp(),
                    visible = true,
                    done = false,
                    activityId = activity.activityId!!
                )

                val id = timerTrackingInsertUseCase.invoke(timerTracking)
                Timber.tag(TAG).d("newTimer() -> new register added with id: $id")

            } catch (e: IndexOutOfBoundsException) {
                //TODO pausa temporal para identificar la causa de esta exception
                var pausa = 0
                pausa++
            }
        }

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
        val activities: List<ActivityEntity> = emptyList(),
        val activeTimers: List<TimersWithActivity> = emptyList(),
        val selectedFilter: Int = 0,
    ) : MonitorScreenUiState

}