/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.toColor
import com.budoxr.ett.commons.toColorName
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale.getDefault

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityFormViewModel(
    private val activityId: Long,
    private val activityInfo: ActivityInfoUseCase,
    private val activityInsertUseCase: ActivityInsertUseCase
) : KoinViewModel() {

    private val _formState = MutableStateFlow(ActivityFormState())
    val formState : StateFlow<ActivityFormState>
        get() = _formState.asStateFlow()

    private val _uiState = MutableStateFlow<ActivityFormUiState>(ActivityFormUiState.Loading)
    val uiState: StateFlow<ActivityFormUiState>
        get() = _uiState.asStateFlow()


    private val refreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            if (activityId > 0) {
                activityInfo.invoke(activityId)
                    .filterNotNull()
                    .collectLatest { activity ->
                        _formState.update {
                            it.copy(
                                name = activity.name,
                                color = activity.color ?: "gray",
                                isValid = activity.name.isNotBlank()
                            )
                        }
                    }
            }
        }

        viewModelScope.launch {
            combine(
                refreshing,
                _formState
            ) { refreshing, _ ->
                if (refreshing) {
                    ActivityFormUiState.Loading
                } else {
                    ActivityFormUiState.Form(errorCode = null)
                }
            }.catch { throwable ->
                throwable.printStackTrace()
                _uiState.value = ActivityFormUiState.Form( errorCode = ErrorCode.DatabaseError.code )
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


    fun onNameChanged(name: String) {
        _formState.update {
            it.copy(
                name = name,
                nameError = when {
                    name.isBlank() -> ErrorCode.FieldRequired.code
                    name.length < 3 -> ErrorCode.NameMinLength.code
                    else -> null
                },
                isValid = name.isNotBlank() && name.length >= 3
            )
        }
    }

    fun onColorChanged(color: String) {
        _formState.update {
            it.copy(
                color = color,
                colorError = null,
            )
        }
    }

    fun onSaveClick() {
        Timber.tag(TAG).d("onSaveClick() -> called.")

        val form = _formState.value

        viewModelScope.launch(Dispatchers.IO) {
            val activityEntity = ActivityEntity(
                activityId = if (activityId > 0) activityId else null,
                name = form.name.uppercase(getDefault()),
                color = form.color
            )
            Timber.tag(TAG).i("Saving activity in the database")
            activityInsertUseCase.invoke(activityEntity)
        }

    }

    fun onCleanForm() {
        _formState.update {
            it.copy(
                name = "",
                color = "",
                nameError = null,
                colorError = null,
                isValid = false,
            )
        }
    }

    companion object {
        private const val TAG = "che.ActivityFormViewModel"
    }

}


sealed interface ActivityFormUiState {
    data object Loading : ActivityFormUiState
    data class Form(val errorCode: Int? = null) : ActivityFormUiState
}

data class ActivityFormState(
    val name: String = "",
    val color: String = "",
    val nameError: Int? = null,
    val colorError: Int? = null,
    val isValid: Boolean = false,
)

enum class ErrorCode(val code: Int) {
    FieldRequired(100),
    NameMinLength(101),
    DatabaseError(103);

    /**
     * Optional utility function to look up an ErrorCode by its raw integer value.
     */
    companion object {
        fun fromCode(code: Int): ErrorCode? = entries.find { it.code == code }
    }
}