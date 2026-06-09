/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.datastore.repositories.UserPreferencesRepository
import com.budoxr.ett.presentation.domain.BarChartItemModel
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeDailyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeMonthlyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeWeeklyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeYesterdayInfoUseCase
import com.budoxr.ett.ui.navigation.Screens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class WeeklyBarChartViewModel(
    private val activityElapsedTimeDailyInfoUseCase: ActivityElapsedTimeDailyInfoUseCase,
    private val activityElapsedTimeYesterdayInfoUseCase: ActivityElapsedTimeYesterdayInfoUseCase,
    private val activityElapsedTimeWeeklyInfoUseCase: ActivityElapsedTimeWeeklyInfoUseCase,
    private val activityElapsedTimeMonthlyInfoUseCase: ActivityElapsedTimeMonthlyInfoUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : KoinViewModel() {

    private val _period = MutableStateFlow<DatePeriod>(DatePeriod.Weekly)
    val period: StateFlow<DatePeriod>
        get() = _period.asStateFlow()

    private val _uiState = MutableStateFlow<WeeklyBarChartScreenUiState>(WeeklyBarChartScreenUiState.Loading)
    val uiState: StateFlow<WeeklyBarChartScreenUiState>
        get() = _uiState.asStateFlow()


    private val refreshing = MutableStateFlow(false)

    init {
        saveLastScreen()

        viewModelScope.launch {
            _period.flatMapLatest { currentPeriod ->
                when(currentPeriod) {
                    DatePeriod.Today -> activityElapsedTimeDailyInfoUseCase.invoke()
                    DatePeriod.Yesterday -> activityElapsedTimeYesterdayInfoUseCase.invoke()
                    DatePeriod.Weekly -> activityElapsedTimeWeeklyInfoUseCase.invoke()
                    DatePeriod.Monthly -> activityElapsedTimeMonthlyInfoUseCase.invoke()
                    DatePeriod.ByRange -> activityElapsedTimeWeeklyInfoUseCase.invoke()
                }
            }.combine(refreshing) { totalTimeActivities, refreshing ->
                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine WeeklyBarChartScreenUiState.Loading
                }

                val items = totalTimeActivities.map { activity ->
                    BarChartItemModel(
                        key = activity.activity.activityId!!,
                        value = activity.totalElapsedTime.toFloat(),
                        label = activity.activity.name,
                        color = activity.activity.color!!
                    )
                }

                WeeklyBarChartScreenUiState.Ready(
                    labelPeriod = when(_period.value) {
                        DatePeriod.Today -> TimeUtils.getDailyPeriod()
                        DatePeriod.Yesterday -> TimeUtils.getYesterdayPeriod()
                        DatePeriod.Weekly -> TimeUtils.getWeekPeriod()
                        DatePeriod.Monthly -> TimeUtils.getMonthlyPeriod()
                        else -> TimeUtils.getWeekPeriod()
                    },
                    period = period.value,
                    items = items
                )

            }.catch { throwable ->
                throwable.printStackTrace()
                _uiState.value = WeeklyBarChartScreenUiState.Error( errorMessage = "error: ${throwable.message}" )
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


    fun changePeriod(period: DatePeriod) {
        Timber.tag(TAG).i("changePeriod() -> invoked, period: $period")
        _period.update { period }
    }


    private fun saveLastScreen() {
        val baseRoute = Screens.WeeklyBarChartScreen.baseRoute
        viewModelScope.launch {
            userPreferencesRepository.saveLastScreen(baseRoute)
            Timber.tag(TAG).i("save the last screen: $baseRoute")
        }
    }


    enum class DatePeriod(val value: String) {
        Today("Today"),
        Yesterday("Yesterday"),
        Weekly("Weekly"),
        Monthly("Monthly"),
        ByRange("By Range");

        companion object {
            fun fromString(value: String): DatePeriod {
                return entries.find { it.value == value } ?: Today
            }
            fun fromIndex(value: Int): DatePeriod {
                return entries.find { it.ordinal == value } ?: Today
            }
        }
    }

    companion object {
        private const val TAG = "che.WeeklyBarChartViewModel"
    }
}

sealed interface WeeklyBarChartScreenUiState {
    data object Loading : WeeklyBarChartScreenUiState

    data class Error(
        val errorMessage: String? = null
    ) : WeeklyBarChartScreenUiState

    data class Ready(
        val labelPeriod: Pair<String,String>? = null,
        val period: WeeklyBarChartViewModel.DatePeriod? = null,
        val items: List<BarChartItemModel> = emptyList(),
    ) : WeeklyBarChartScreenUiState

}
