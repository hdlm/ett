/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.budoxr.ett.R
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme

@Composable
fun MainBottomBar(
    navController: NavHostController,
    selectedRoute: String? = null
) {
    val currentRoute = selectedRoute ?: currentRoute(navController)

    val navigationItems = listOf(
        Screens.MonitorScreen,
        Screens.ActivityScreen,
        Screens.WeeklyBarChartScreen,
        Screens.SettingScreen
    )

    currentRoute?.let {
        BottomAppBar {
            BottomNavigationBar(
                navController = navController,
                items = navigationItems,
                selectedRoute = it
            )
        }
    }
}


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screens>,
    selectedRoute: String?
) {
    NavigationBar {
        items.forEach { screen ->
            val isSelected = selectedRoute == screen.route || 
                             (selectedRoute != null && screen.baseRoute == getBaseRoute(selectedRoute))

            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = stringResource(R.string.content_description_icon) ) },
                label = { BottomNavigationBarText(selected = isSelected, label = stringResource(screen.titleResId) ) },
                selected = isSelected,
                onClick = {
                    val destination = when (screen) {
                        // Special case: ActivityFormScreen requires an initial '0' argument
                        Screens.ActivityFormScreen -> "${Screens.ActivityFormScreen.baseRoute}/0"
                        // Default case: Use the defined route for all others
                        else -> screen.route
                    }

                    navController.navigate(destination) {
                        // Apply the robust popUpTo logic for consistent back-stack behavior
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid creating multiple copies of the same destination on the stack
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true,
            )
        }
    }
}


@Composable
private fun BottomNavigationBarText(
    selected: Boolean,
    label: String,
) {
    if(selected) {
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.primary)
    } else {
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


fun getBaseRoute(route: String): String =
    route.substringBefore('/')


@Composable
@Preview(showBackground = true)
private fun BottomNavigationBarPreview() {
    val horizontalMargin = dimensionResource(R.dimen.margin_horizontal)
    val isDarkTheme = false
    val navController = NavHostController(LocalContext.current)

    EasyTimeTrackingTheme(darkTheme = isDarkTheme, dynamicColor = false) {

        Scaffold( modifier = Modifier.systemBarsPadding(),
            topBar = {
                GlobalTopBar(
                    isDarkTheme = isDarkTheme,
                    navIcon = Screens.MonitorScreen.icon,
                    onBackButtonClick = {},
                    titleIcon = null,
                    title = stringResource(Screens.MonitorScreen.titleResId),
                    actionIcon = null,
                    onActionButtonClick = {},
                )
            },
            bottomBar = {
                MainBottomBar(
                    navController = navController,
                    selectedRoute = Screens.MonitorScreen.route
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {}
        ) { innerPadding ->

            Surface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Column(modifier = Modifier.padding(horizontalMargin),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text("This is a BottomBar preview.")
                }
            }

        }

    }
}


private const val TAG = "che.MainBottomBar"
