package com.budoxr.ett.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.presentation.presenters.MonitorViewModel
import com.budoxr.ett.ui.components.GlobalTopBar
import com.budoxr.ett.ui.components.MainBottomBar
import com.budoxr.ett.ui.navigation.Screens
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun MonitorScreen(
    isDarkTheme: Boolean,
    navController: NavHostController,
    onBackButtonClick: onDismissType,
    viewModel: MonitorViewModel = koinViewModel()
) {
    Timber.tag(TAG).i("compose / recompose")

    MonitorContentScreen(
        isDarkTheme = isDarkTheme,
        navIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onBackButtonClick = onBackButtonClick,
        titleIcon = null,
        title = stringResource(Screens.MonitorScreen.titleResId),
        navController = navController,
    )

}

@Composable
fun MonitorContentScreen(
    isDarkTheme: Boolean,
    navController: NavHostController,
    navIcon: ImageVector?,
    onBackButtonClick: onDismissType,
    titleIcon: ImageVector?,
    title: String,
) {
    Scaffold (
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton =  { /* hide the button  */ },
        topBar = {
            GlobalTopBar(
                isDarkTheme = isDarkTheme,
                navIcon = navIcon,
                onBackButtonClick = onBackButtonClick,
                titleIcon = titleIcon,
                title = title,
                actionIcon = null,
                onActionButtonClick = {},
            )
        },
        bottomBar = { MainBottomBar(navController) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            MonitorBodyScreen()
        }
    }
}


@Composable
fun MonitorBodyScreen() {
    Text("hola mundo!!!")

}

private const val TAG = "che.MonitorScreen"