package com.budoxr.ett.presentation.presenters

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ManageBackupViewModel(
    private val typeOperation: Int
) : KoinViewModel() {

    private val _uiState = MutableStateFlow<ManageBackupUiState>(ManageBackupUiState.Ready(TypeOperation.fromValue(typeOperation)))
    val uiState: StateFlow<ManageBackupUiState>
        get() = _uiState.asStateFlow()
}


sealed interface ManageBackupUiState {
    data class Ready(
        val typeOperation: TypeOperation = TypeOperation.Backup,
        val isLoading: Boolean = false
    ) : ManageBackupUiState


    data class Success(
        val typeOperation: TypeOperation = TypeOperation.Backup,
        val errorMessage: String? = null
    ) : ManageBackupUiState

}


enum class TypeOperation(val value: Int) {
    Backup(0),
    Restore(1);

    companion object {
        fun fromValue(value: Int): TypeOperation =
            entries.find { it.value == value } ?: Backup
    }
}