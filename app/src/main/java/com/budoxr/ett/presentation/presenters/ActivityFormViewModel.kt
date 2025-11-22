package com.budoxr.ett.presentation.presenters

import androidx.compose.ui.graphics.toColorLong
import androidx.lifecycle.viewModelScope
import com.budoxr.ett.commons.toColor
import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import timber.log.Timber

class ActivityFormViewModel : KoinViewModel() {
    private val activityInsertUseCase: ActivityInsertUseCase by inject()

    private val _uiState = MutableStateFlow<ActivityFormUiState>(ActivityFormUiState.Form())
    val uiState: StateFlow<ActivityFormUiState>
        get() = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(ActivityFormState())
    val formState : StateFlow<ActivityFormState>
        get() = _formState.asStateFlow()

    fun onNameChanged(name: String) {
        _formState.update {
            it.copy(
                name = name,
                nameError = when {
                    name.isBlank() -> ErrorCode.FIELD_REQUIRED.code
                    name.length < 3 -> ErrorCode.NAME_MIN_LENGTH.code
                    else -> null
                },
            )
        }
        validateForm()

    }

    fun onColorChanged(color: String) {
        _formState.update {
            it.copy(
                color = color,
                colorError = null,
            )
        }
    }


    private fun validateForm() {
        _uiState.value = ActivityFormUiState.Form(
            errorType = if (_formState.value.name.isBlank()) {
                ErrorCode.FIELD_REQUIRED.code
            } else {
                null
            }
        )
        _formState.update {
            it.copy(
                isValid = it.name.isNotBlank()
            )
        }
    }

    fun onSaveClick() {
        Timber.tag(TAG).d("onSaveClick() -> called.")

        val form = _formState.value

        viewModelScope.launch(Dispatchers.IO) {
            val activityEntity = ActivityEntity(
                name = form.name,
                color = form.color.toColor().toColorLong()
            )
            Timber.tag(TAG).i("Saving new activity in the database")
            activityInsertUseCase(activityEntity)
        }

    }

    companion object {
        private const val TAG = "che.ActivityFormViewModel"
    }

}


sealed interface ActivityFormUiState {
    data class Form(val errorType: Int? = null) : ActivityFormUiState
}

data class ActivityFormState(
    val name: String = "",
    val color: String = "",
    val nameError: Int? = null,
    val colorError: Int? = null,
    val isValid: Boolean = false,
)

enum class ErrorCode(val code: Int) {
    FIELD_REQUIRED(100),
    NAME_MIN_LENGTH(101);

    /**
     * Optional utility function to look up an ErrorCode by its raw integer value.
     */
    companion object {
        fun fromCode(code: Int): ErrorCode? = entries.find { it.code == code }
    }
}