/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.presentation.presenters.ManageBackupUiState
import com.budoxr.ett.presentation.presenters.ManageBackupViewModel
import com.budoxr.ett.presentation.presenters.TypeOperation
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

    val (typeOperation, isLoading, errorMessage) = when (val uiState = manageBackupUiState) {
        is ManageBackupUiState.Ready -> Triple(uiState.typeOperation, uiState.isLoading, null)
        is ManageBackupUiState.Success -> Triple(uiState.typeOperation, false, uiState.errorMessage)
    }
    Timber.tag(TAG).d("typeOperation: $typeOperation, isLoading: $isLoading, errorMessage: $errorMessage")

    ManageBackupScreenContent(
        navController = navController,
        typeOperation = typeOperation,
        isLoading = isLoading,
        errorMessage = errorMessage,
        isDarkTheme = isDarkTheme,
        onBackButtonClick = onBackButtonClick
    )
}


@Composable
private fun ManageBackupScreenContent(
    navController: NavHostController,
    typeOperation: TypeOperation,
    isLoading: Boolean,
    errorMessage: String?,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
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
                    typeOperation = typeOperation,
                    isLoading = isLoading,
                )
                TypeOperation.Restore -> ManageBackupScreenRestore(
                    typeOperation = typeOperation,
                    errorMessage = errorMessage,
                )
            }
        }
    }
}


@Composable
private fun ManageBackupScreenBackup(
    typeOperation: TypeOperation,
    isLoading: Boolean,
) {
    // TODO: Implement Backup UI
}

@Composable
private fun ManageBackupScreenRestore(
    typeOperation: TypeOperation,
    errorMessage: String? = null,
) {
    // TODO: Implement Restore UI
}

@Preview(showBackground = true)
@Composable
fun ManageBackupScreenBackupPreview() {
    val navController = rememberNavController()
    val isDarkTheme = false
    val typeOperation = TypeOperation.Restore
    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        ManageBackupScreenContent(
            navController = navController,
            typeOperation = typeOperation,
            isLoading = false,
            errorMessage = null,
            isDarkTheme = false,
            onBackButtonClick = {}
        )
    }
}



private const val TAG = "che.ManageBackupScreen"
