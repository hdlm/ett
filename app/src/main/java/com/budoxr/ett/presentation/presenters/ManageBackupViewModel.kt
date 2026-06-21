/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.presenters

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.budoxr.ett.R
import com.budoxr.ett.commons.CommonValues
import com.budoxr.ett.commons.utils.CsvHelper
import com.budoxr.ett.commons.utils.FileUtils
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.data.database.DatabaseBackupManager
import com.budoxr.ett.presentation.usecase.ActivitiesWithTimersUseCase
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class ManageBackupViewModel(
    private val typeOperation: Int,
    private val databaseBackupManager: DatabaseBackupManager,
    private val activitiesWithTimersUseCase: ActivitiesWithTimersUseCase,
    private val activityInfoUseCase: ActivityInfoUseCase,
    private val activityInsertUseCase: ActivityInsertUseCase,
    private val timerTrackingUseCase: TimerTrackingInsertUseCase,
    private val fileUtils: FileUtils,
    private val utility: Utility,
    private val cvsHelper: CsvHelper,
    ) : KoinViewModel() {

    private val _csvFilePath = MutableStateFlow<Uri?>(null)
    val csvFilePath: StateFlow<Uri?> = _csvFilePath.asStateFlow()

    private val _jsonFilePath = MutableStateFlow<Uri?>(null)
    val jsonFilePath: StateFlow<Uri?> = _jsonFilePath.asStateFlow()

    private val _uiState = MutableStateFlow<ManageBackupUiState>(ManageBackupUiState.Ready(TypeOperation.fromValue(typeOperation)))
    val uiState: StateFlow<ManageBackupUiState>
        get() = _uiState.asStateFlow()


    fun startBackup() {
        Timber.tag(TAG).d("startBackup() -> called.")
        viewModelScope.launch {
            val currentType = TypeOperation.Backup
            _uiState.value = ManageBackupUiState.Ready(typeOperation = currentType, isLoading = true)
            
            val folder = fileUtils.getDownloadFolder()
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
            
            val folder = fileUtils.getDownloadFolder()
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
                utility.triggerAppRestart()
            } else {
                Timber.tag(TAG).e(result.exceptionOrNull(), "Database restore failed.")
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType, 
                    errorMessage = R.string.message_restore_failed
                )
            }
        }
    }

    fun selectCsvFile(uri: Uri) {
        Timber.tag(TAG).d("selectCsvFile() -> called with uri: $uri")
        _csvFilePath.value = uri
    }


    fun selectJsonFile(uri: Uri) {
        Timber.tag(TAG).d("selectJsonFile() -> called with uri: $uri")
        _jsonFilePath.value = uri
    }

    fun startImportCsv() {
        Timber.tag(TAG).d("startImportCsv() -> called.")
        viewModelScope.launch {
            val currentType = TypeOperation.ImportFromCsv
            val uri = _csvFilePath.value

            if (uri == null) {
                _uiState.value = ManageBackupUiState.Success(
                    typeOperation = currentType,
                    errorMessage = R.string.message_import_failed
                )
                return@launch
            }

            _uiState.value = ManageBackupUiState.Ready(typeOperation = currentType, isLoading = true)

            databaseBackupManager.importFromCsv(uri)
                .fold(
                    onSuccess = { data ->
                        cvsHelper.processCsvRawData(
                            rawData = data,
                            activities = activityInfoUseCase.invoke(true),
                            activityInsertUseCase = activityInsertUseCase,
                            timerTrackingUseCase = timerTrackingUseCase
                        )
                        
                        _uiState.value = ManageBackupUiState.Success(
                            typeOperation = currentType
                        )
                        _csvFilePath.value = null // Clear selection on success
                    },
                    onFailure = { error ->
                        Timber.tag(TAG).e(error, "startImportCsv() -> Import failed.")
                        _uiState.value = ManageBackupUiState.Success(
                            typeOperation = currentType,
                            errorMessage = R.string.message_import_failed
                        )
                    }
                )
        }
    }

    fun startExportJson() {
        Timber.tag(TAG).d("startExportJson() -> called.")
        viewModelScope.launch {
            val currentType = TypeOperation.ExportToJson

            _uiState.value = ManageBackupUiState.Ready(typeOperation = currentType, isLoading = true)

            val activities = utility.performAsyncOperation(
                scope = this,
                timeout = CommonValues.WAIT_DEFERRED,
                timeUnit = TimeUnit.SECONDS,
                dispatcher = Dispatchers.IO
            ) {
                activitiesWithTimersUseCase.invoke()
            }.await().toSortedSet(compareBy { it.activity.name })

            databaseBackupManager.exportToJson(
                activities = activities
            ).fold(
                onSuccess = { 
                    _uiState.value = ManageBackupUiState.Success(
                        typeOperation = currentType
                    )
                    _jsonFilePath.value = null 
                },
                onFailure = { error ->
                    Timber.tag(TAG).e(error, "startExportJson() -> Export failed.")
                    _uiState.value = ManageBackupUiState.Success(
                        typeOperation = currentType,
                        errorMessage = R.string.message_export_failed
                    )
                }
            )  
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
        val backupPath: String? = null,
    ) : ManageBackupUiState

}


enum class TypeOperation(val value: Int) {
    Backup(0),
    Restore(1),
    ImportFromCsv(2),
    ExportToJson(3),
    ImportFromJson(4);

    companion object {
        fun fromValue(value: Int): TypeOperation =
            entries.find { it.value == value } ?: Backup
    }
}

enum class CsvFields(val value: Int) {
    Activity(0),
    SundayValue(1),
    MondayValue(2),
    TuesdayValue(3),
    WednesdayValue(4),
    ThursdayValue(5),
    FridayValue(6),
    SaturdayValue(7),
    Sunday(8),
    Saturday(9);

    companion object {
        fun fromValue(value: Int): CsvFields =
            entries.find { it.value == value } ?: Activity
    }
}