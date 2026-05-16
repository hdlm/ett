/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.presentation.domain.BarChartItemModel
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeWeeklyInfoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class WeeklyBarChartViewModel(
    private val activityElapsedTimeWeeklyInfoUseCase: ActivityElapsedTimeWeeklyInfoUseCase
) : KoinViewModel() {

    private val _totalTimeActivities = MutableStateFlow<List<ActivityElapsedTimeWeeklyInfoUseCase>>(emptyList())

    private val _uiState = MutableStateFlow<WeeklyBarChartScreenUiState>(WeeklyBarChartScreenUiState.Loading)
    val uiState: StateFlow<WeeklyBarChartScreenUiState>
        get() = _uiState.asStateFlow()


    private val refreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch {

            combine(
                _totalTimeActivities.flatMapLatest { _ ->
                    activityElapsedTimeWeeklyInfoUseCase.invoke()
                },
                refreshing
            ) { totalTimeActivities,
                refreshing ->

                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine WeeklyBarChartScreenUiState.Loading
                }

                val items = mutableSetOf<BarChartItemModel>()
                totalTimeActivities.forEach { activity ->
                    val item = BarChartItemModel(
                        key = activity.activity.activityId!!,
                        value = activity.totalElapsedTime.toFloat(),
                        label = activity.activity.name,
                        color = activity.activity.color!!
                    )
                    items.add(item)
                }

                WeeklyBarChartScreenUiState.Ready(
                    period = TimeUtils.getWeekPeriod(),
                    items = items.toList()
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
        val period: Pair<String,String>? = null,
        val items: List<BarChartItemModel> = emptyList(),
    ) : WeeklyBarChartScreenUiState

}
