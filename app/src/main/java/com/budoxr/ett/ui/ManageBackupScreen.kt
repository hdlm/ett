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
        onBackButtonClick = onBackButtonClick,
        onStartBackupClick = { viewModel.startBackup() },
        onRestoreBackupClick = { viewModel.startRestore() },
    )
}


@Composable
private fun ManageBackupScreenContent(
    navController: NavHostController,
    typeOperation: TypeOperation,
    isLoading: Boolean,
    @StringRes
    errorMessage: Int?,
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
        }
    }
}

@Composable
private fun ManageBackupScreenRestore(
    typeOperation: TypeOperation,
    @StringRes
    errorMessage: Int? = null,
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

        errorMessage?.let {
            Text(
                text = stringResource(it),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

    }

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
            isLoading = true,
            errorMessage = null,
            isDarkTheme = false,
            onBackButtonClick = {},
            onStartBackupClick = {},
            onRestoreBackupClick = {}
        )
    }
}



private const val TAG = "che.ManageBackupScreen"
