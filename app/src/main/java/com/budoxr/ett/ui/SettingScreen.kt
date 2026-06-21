/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.R
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.presentation.presenters.TypeOperation
import com.budoxr.ett.ui.components.ButtonConfirm
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import timber.log.Timber


@Composable
fun SettingScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    navigateToManageBackup: onIntType,
    navigateToAbout: onDismissType
) {
    Timber.tag(TAG).i("compose / recompose")

    SettingContent(
        navController = navController,
        isDarkTheme = isDarkTheme,
        onBackButtonClick = onBackButtonClick,
        navigateToManageBackup = navigateToManageBackup,
        navigateToAbout = navigateToAbout
    )

}

    @Composable
fun SettingContent(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onBackButtonClick: onDismissType,
    navigateToManageBackup: onIntType,
    navigateToAbout: onDismissType
) {
    val lineSpacing = dimensionResource(R.dimen.line_spacing_1)
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)


    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = Screens.SettingScreen.icon,
                onBackButtonClick = onBackButtonClick,
                titleIcon = null,
                title = stringResource(Screens.SettingScreen.titleResId),
                actionIcon = null,
                onActionButtonClick = {},
            )
        },
        bottomBar = { MainBottomBar(navController) }
    ) { innerPadding ->

        Column( modifier = Modifier.fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = horizontalMargin),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            ButtonConfirm(
                modifier = Modifier,
                label = stringResource(R.string.label_make_backup),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToManageBackup(TypeOperation.Backup.value) },
            )

            ButtonConfirm(
                modifier = Modifier,
                label = stringResource(R.string.label_restore_backup),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToManageBackup(TypeOperation.Restore.value) },
            )

            ButtonConfirm(
                modifier = Modifier
                    .padding(bottom = lineSpacing),
                label = stringResource(R.string.title_manage_backup_cvs_import),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToManageBackup(TypeOperation.ImportFromCsv.value) },
            )

            ButtonConfirm(
                modifier = Modifier
                    .padding(bottom = lineSpacing),
                label = stringResource(R.string.title_manage_backup_json_export),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToManageBackup(TypeOperation.ExportToJson.value) },
            )

            ButtonConfirm(
                modifier = Modifier
                    .padding(bottom = lineSpacing),
                label = stringResource(R.string.title_manage_backup_json_import),
                isEnabled = true,
                showTopBorderLine = false,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToManageBackup(TypeOperation.ImportFromJson.value) },
            )

            ButtonConfirm(
                modifier = Modifier,
                label = stringResource(R.string.title_about_screen),
                isEnabled = true,
                showTopBorderLine = true,
                buttonIcon = null,
                buttonVector = null,
                buttonImg = null,
                onConfirmClick = { navigateToAbout() },
            )

        }

    }

}

@Composable
@Preview(showBackground = true)
fun SettingScreenPreview() {
    val navController = rememberNavController()
    val isDarkTheme = true

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {
        SettingContent(
            navController = navController,
            isDarkTheme = isDarkTheme,
            onBackButtonClick = {},
            navigateToManageBackup = {},
            navigateToAbout = {}
        )

    }
}

private const val TAG = "che.SettingScreen"