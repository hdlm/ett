/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.commons.onIntType
import com.budoxr.ett.commons.onLongType
import com.budoxr.ett.presentation.presenters.ActivityFormViewModel
import com.budoxr.ett.ui.AboutScreen
import com.budoxr.ett.ui.ActivityFormScreen
import com.budoxr.ett.ui.ActivityScreen
import com.budoxr.ett.ui.MonitorScreen
import com.budoxr.ett.ui.WeeklyBarChartScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.Locale


@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    startDest: Screens,
) {
    val navigateToMonitor: onDismissType = {
        val destination = Screens.MonitorScreen.baseRoute
        navController.navigate(destination)
    }
    val navigateToActivity: onDismissType = {
        val destination = Screens.ActivityScreen.baseRoute
        navController.navigate(destination)
    }

    val navigateToActivityForm: onLongType = { id ->
        Timber.tag(TAG).i("navigateToActivityForm() -> called, id: $id")
        val screenName = Screens.ActivityFormScreen.baseRoute
        val destination = "${screenName}/$id"
        navController.navigate(destination)
    }


    NavHost(navController = navController, startDestination = startDest.route ) {
        composable(
            route = Screens.MonitorScreen.route
        ) { _ ->
            val onBackButtonClick: onDismissType = {
                Timber.tag(TAG).d("onBackButtonClick() -> clicked")
                navController.navigate(Screens.MonitorScreen.baseRoute) {
                    // Limpia la pila de retroceso para que MonitorScreen sea la única pantalla
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true // Evita múltiples instancias de la misma pantalla
                }
            }
            MonitorScreen(
                isDarkTheme = isDarkTheme,
                navController = navController,
                onBackButtonClick = onBackButtonClick,
            )
        }

        composable(
            route = Screens.ActivityScreen.route
        ) { _ ->
            val onBackButtonClick: onDismissType = {
                Timber.tag(TAG).d("onBackButtonClick() -> clicked")
                navController.navigate(Screens.ActivityScreen.baseRoute) {
                    // Limpia la pila de retroceso para que MonitorScreen sea la única pantalla
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true // Evita múltiples instancias de la misma pantalla
                }
            }
            ActivityScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onBackButtonClick = onBackButtonClick,
                navigateToActivityForm = navigateToActivityForm,
            )
        }

        composable(
            route = Screens.ActivityFormScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getLong("id") ?: 0L
            val viewModel = koinViewModel<ActivityFormViewModel>(viewModelStoreOwner = backStackEntry, parameters = { parametersOf(activityId) })

            val onBackButtonClick: onDismissType = {
                val value = navController.popBackStack()
                Timber.tag(TAG).i("onBackButtonClick() -> clicked\n\treturned value: $value")
            }

            ActivityFormScreen(
                id = activityId,
                isDarkTheme = isDarkTheme,
                onBackButtonClick = onBackButtonClick,
                viewModel = viewModel
            )
        }


        composable(
            route = Screens.WeeklyBarChartScreen.route
        ) { _ ->
            val onBackButtonClick: onDismissType = {
                Timber.tag(TAG).d("onBackButtonClick() -> clicked")
                navController.navigate(Screens.WeeklyBarChartScreen.baseRoute) {
                    // Limpia la pila de retroceso para que MonitorScreen sea la única pantalla
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true // Evita múltiples instancias de la misma pantalla
                }
            }
            WeeklyBarChartScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onBackButtonClick = onBackButtonClick,
            )
        }


        composable(
            route = Screens.AboutScreen.route
        ) { _ ->
            AboutScreen(
                language = Locale.getDefault().language,
                isDarkTheme = isDarkTheme,
                onBackButtonClick = navigateToMonitor
            )
        }

    }

}

private const val TAG = "che.AppNavigation"