package com.budoxr.ett.presentation.presenters

import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.utils.combine
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import timber.log.Timber


class ActivityViewModel : KoinViewModel() {
    private val activityInfoUseCase: ActivityInfoUseCase by inject()

    // Estado interno
    private val refreshing = MutableStateFlow(false)
    private val _activities = MutableStateFlow<List<ActivityEntity>>(emptyList()) // Asumo List<ActivityEntity> o similar
    private val _uiState = MutableStateFlow<ActivityScreenUiState>(ActivityScreenUiState.Loading)
    val uiState: StateFlow<ActivityScreenUiState> = _uiState.asStateFlow()

    init {
        Timber.tag(TAG).i("init() -> called")
        viewModelScope.launch(Dispatchers.IO) {
            combine(_activities, refreshing) { activities, isRefreshing ->
                ActivityScreenUiState.Ready(
                    activities = activities,
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


    companion object {
        private const val TAG = "che.ActivityViewModel"
    }
}

sealed interface ActivityScreenUiState {
    data object Loading : ActivityScreenUiState

    data class Error(
        val message: String? = null
    ) : ActivityScreenUiState

    data class Ready(
        val activities: List<ActivityEntity>,
    ) : ActivityScreenUiState

}