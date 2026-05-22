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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

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
            val currentType = TypeOperation.Backup
            _uiState.value = ManageBackupUiState.Ready(typeOperation = currentType, isLoading = true)
            
            val folder = databaseBackupManager.getDownloadFolder()
            if (folder == null) {
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType, 
                    errorMessage = R.string.message_backup_failed
                )
                return@launch
            }

            val backupFile = File(folder, BACKUP_FILE_NAME)
            databaseBackupManager.backupDatabase(backupFile)
                .fold(
                    onSuccess = {
                        _uiState.value = ManageBackupUiState.Success(
                            typeOperation = currentType,
                            backupPath = backupFile.absolutePath
                        )
                    },
                    onFailure = {
                        _uiState.value = ManageBackupUiState.Success(
                            typeOperation = currentType, 
                            errorMessage = R.string.message_backup_failed 
                        )
                    }
                )
            Timber.tag(TAG).i("backing up the database.")
        }
    }

    fun startRestore() {
        Timber.tag(TAG).d("startRestore() -> called.")
        viewModelScope.launch {
            val currentType = TypeOperation.Restore
            _uiState.value = ManageBackupUiState.Ready(typeOperation = currentType, isLoading = true)
            
            val folder = databaseBackupManager.getDownloadFolder()
            if (folder == null) {
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType, 
                    errorMessage = R.string.message_restore_failed
                )
                return@launch
            }

            val backupFile = File(folder, BACKUP_FILE_NAME)
            val result = databaseBackupManager.restoreDatabase(backupFile)
            
            if (result.isSuccess) {
                Timber.tag(TAG).i("Database restored successfully. Updating UI state...")
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType,
                    backupPath = backupFile.absolutePath
                )
                
                // Wait for the UI to reflect the success message and logs to flush
                delay(1500) 
                
                Timber.tag(TAG).i("Triggering app restart now.")
                databaseBackupManager.triggerAppRestart()
            } else {
                Timber.tag(TAG).e(result.exceptionOrNull(), "Database restore failed.")
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType, 
                    errorMessage = R.string.message_restore_failed
                )
            }
        }
    }

    companion object {
        private const val TAG = "che.ManageBackupViewModel"
        private const val BACKUP_FILE_NAME = "ett_backup.db"
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
        val errorMessage: Int? = null,
        val backupPath: String? = null
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
