/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.presentation.presenters.ManageBackupUiState
import com.budoxr.ett.presentation.presenters.ManageBackupViewModel
import com.budoxr.ett.presentation.presenters.TypeOperation
import com.budoxr.ett.ui.components.ButtonConfirm
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import timber.log.Timber

@Composable
fun ManageBackupScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    viewModel: ManageBackupViewModel
) {
    Timber.tag(TAG).i("compose / recompose")

    val manageBackupUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val (typeOperation, isLoading, errorMessage, isSuccess, backupPath) = when (val uiState = manageBackupUiState) {
        is ManageBackupUiState.Ready -> quintupleOf(uiState.typeOperation, uiState.isLoading, null, false, null)
        is ManageBackupUiState.Success -> quintupleOf(uiState.typeOperation, false, uiState.errorMessage, uiState.errorMessage == null, uiState.backupPath)
    }
    Timber.tag(TAG).d("typeOperation: $typeOperation, isLoading: $isLoading, errorMessage: $errorMessage, isSuccess: $isSuccess, backupPath: $backupPath")

    ManageBackupScreenContent(
        navController = navController,
        typeOperation = typeOperation,
        isLoading = isLoading,
        errorMessage = errorMessage,
        isSuccess = isSuccess,
        backupPath = backupPath,
        isDarkTheme = isDarkTheme,
        onBackButtonClick = onBackButtonClick,
        onStartBackupClick = { viewModel.startBackup() },
        onRestoreBackupClick = { viewModel.startRestore() },
    )
}

private data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)

private fun <A, B, C, D, E> quintupleOf(a: A, b: B, c: C, d: D, e: E): Quintuple<A, B, C, D, E> = Quintuple(a, b, c, d, e)

@Composable
private fun ManageBackupScreenContent(
    navController: NavHostController,
    typeOperation: TypeOperation,
    isLoading: Boolean,
    @StringRes
    errorMessage: Int?,
    isSuccess: Boolean,
    backupPath: String?,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    onStartBackupClick: onDismissType,
    onRestoreBackupClick: onDismissType
) {
    Scaffold(
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Icons.Outlined.Save,
                onBackButtonClick = onBackButtonClick,
                titleIcon = null,
                title = when (typeOperation) {
                    TypeOperation.Backup -> stringResource(R.string.title_manage_backup_make)
                    TypeOperation.Restore -> stringResource(R.string.title_manage_backup_restore)
                },
                actionIcon = null,
                onActionButtonClick = {}
            )
        },
        bottomBar = { 
            MainBottomBar(
                navController = navController, 
                selectedRoute = Screens.SettingScreen.route
            ) 
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxWidth()
            .padding(innerPadding)
        ) {
            when (typeOperation) {
                TypeOperation.Backup -> ManageBackupScreenBackup(
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    isSuccess = isSuccess,
                    backupPath = backupPath,
                    onStartBackupClick = onStartBackupClick
                )
                TypeOperation.Restore -> ManageBackupScreenRestore(
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    isSuccess = isSuccess,
                    onRestoreBackupClick = onRestoreBackupClick
                )
            }
        }
    }
}


@Composable
private fun ManageBackupScreenBackup(
    isLoading: Boolean,
    @StringRes
    errorMessage: Int?,
    isSuccess: Boolean,
    backupPath: String?,
    onStartBackupClick: onDismissType,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ett_logo),
            contentDescription = stringResource(id = R.string.content_description_ett_logo),
            modifier = Modifier.size(120.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.label_start_backup),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            ButtonConfirm(
                label = stringResource(id = R.string.label_make_backup),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = onStartBackupClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSuccess) {
            Text(
                text = stringResource(id = R.string.message_backup_done),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            backupPath?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        errorMessage?.let {
            Text(
                text = stringResource(it),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ManageBackupScreenRestore(
    isLoading: Boolean,
    @StringRes
    errorMessage: Int? = null,
    isSuccess: Boolean,
    onRestoreBackupClick: onDismissType,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ett_logo),
            contentDescription = stringResource(id = R.string.content_description_ett_logo),
            modifier = Modifier.size(120.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.label_start_restore),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            ButtonConfirm(
                label = stringResource(id = R.string.label_restore_backup),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = onRestoreBackupClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSuccess) {
            Text(
                text = stringResource(id = R.string.message_restore_done),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }

        errorMessage?.let {
            Text(
                text = stringResource(it),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageBackupScreenBackupPreview() {
    val navController = rememberNavController()
    val isDarkTheme = false
    val typeOperation = TypeOperation.Backup
    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        ManageBackupScreenContent(
            navController = navController,
            typeOperation = typeOperation,
            isLoading = false,
            errorMessage = null,
            isSuccess = true,
            backupPath = "/storage/emulated/0/Android/data/com.budoxr.ett/files/Download/ett_backup.db",
            isDarkTheme = false,
            onBackButtonClick = {},
            onStartBackupClick = {},
            onRestoreBackupClick = {}
        )
    }
}



private const val TAG = "che.ManageBackupScreen"
