/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.components

import com.budoxr.ett.R
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.gray
import com.budoxr.ett.ui.theme.grayLight

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
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
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
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Normal, color = gray)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


fun getBaseRoute(route: String): String =
    route.substringBefore('/')


private const val TAG = "che.MainBottomBar"
