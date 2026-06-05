/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.datastore.repositories.UserPreferencesRepository
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.ui.navigation.Screens
import kotlinx.coroutines.Dispatchers
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
class ActivityViewModel(
    private val activityInfoUseCase: ActivityInfoUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : KoinViewModel() {
    private val _activities = MutableStateFlow<List<ActivityEntity>>(emptyList())
    private val _formState = MutableStateFlow(ActivityState())
    val formState : StateFlow<ActivityState>
        get() = _formState.asStateFlow()

    private val refreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = refreshing.asStateFlow()

    private val _uiState = MutableStateFlow<ActivityScreenUiState>(ActivityScreenUiState.Loading)
    val uiState: StateFlow<ActivityScreenUiState> = _uiState.asStateFlow()

    init {
        Timber.tag(TAG).i("init() -> called")
        
        // Save the last screen only once upon initialization
        saveLastScreen()

        viewModelScope.launch(Dispatchers.IO) {
            combine(
                _activities.flatMapLatest { _ ->
                    activityInfoUseCase.invoke()
                },
                _formState,
                refreshing
            ) { setActivities,
                formState,
                refreshing ->

                if (refreshing) {
                    Timber.tag(TAG).d("refreshing: $refreshing")
                    return@combine ActivityScreenUiState.Loading
                }

                val activities = setActivities.toList()

                val filteredActivities = activities.filter {
                    it.name.contains(formState.search, ignoreCase = true)
                }

                ActivityScreenUiState.Ready(
                    activities = filteredActivities.ifEmpty { activities },
                    activityState = formState,
                )

            }.catch { throwable ->
                throwable.printStackTrace()
                Timber.tag(TAG).e("error: ${throwable.localizedMessage}")
                _uiState.update { ActivityScreenUiState.Error(throwable.message) }
            }.collect { _uiState.value = it }
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

    fun onSearchChange(value: String) {
        Timber.tag(TAG).i("onSearchChange() -> called, value: $value")
        _formState.update {
            it.copy(
                search = value
            )
        }
    }

    private fun saveLastScreen() {
        val baseRoute = Screens.ActivityScreen.baseRoute
        viewModelScope.launch {
            userPreferencesRepository.saveLastScreen(baseRoute)
            Timber.tag(TAG).i("save the last screen: $baseRoute")
        }
    }


    companion object {
        private const val TAG = "che.ActivityViewModel"
    }
}

sealed interface ActivityScreenUiState {
    data object Loading : ActivityScreenUiState

    data class Error(
        val errorMessage: String? = null
    ) : ActivityScreenUiState

    data class Ready(
        val activities: List<ActivityEntity>,
        val activityState: ActivityState,
    ) : ActivityScreenUiState

}

data class ActivityState(
    val search: String = "",
)
