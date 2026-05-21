/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import com.budoxr.ett.R
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.budoxr.ett.data.database.DatabaseBackupManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ManageBackupViewModel(
    private val typeOperation: Int,
    private val databaseBackupManager: DatabaseBackupManager
) : KoinViewModel() {

    private val _uiState = MutableStateFlow<ManageBackupUiState>(ManageBackupUiState.Ready(TypeOperation.fromValue(typeOperation)))
    val uiState: StateFlow<ManageBackupUiState>
        get() = _uiState.asStateFlow()

    fun startBackup() {
        Timber.tag(TAG).d("startBackup() -> called.")
        viewModelScope.launch {
            _uiState.value = ManageBackupUiState.Ready(isLoading = true)
            databaseBackupManager
                .backupDatabase(databaseBackupManager.getDownloadFolder()!!)
                .fold(
                    onSuccess = {
                        _uiState.value = ManageBackupUiState.Success(typeOperation = TypeOperation.Backup)
                    },
                    onFailure = {
                        _uiState.value = ManageBackupUiState.Success(typeOperation = TypeOperation.Backup, errorMessage = R.string.message_backup_failed )
                    }
                )
            Timber.tag(TAG).i("backing up the database.")
        }
    }

    fun startRestore() {
        //TODO not implemented yet
    }

    companion object {
        private const val TAG = "che.ManageBackupViewModel"
    }
}


sealed interface ManageBackupUiState {
    data class Ready(
        val typeOperation: TypeOperation = TypeOperation.Backup,
        val isLoading: Boolean = false,
    ) : ManageBackupUiState


    data class Success(
        val typeOperation: TypeOperation = TypeOperation.Backup,
        @field:StringRes
        val errorMessage: Int? = null
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