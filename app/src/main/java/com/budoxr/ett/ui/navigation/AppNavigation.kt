package com.budoxr.ett.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.budoxr.ett.commons.onDismissType
import com.budoxr.ett.ui.ActivityFormScreen
import com.budoxr.ett.ui.MonitorScreen
import timber.log.Timber

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    startDest: Screens,
) {

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
            route = Screens.ActivityFormScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")!!

            val onBackButtonClick: onDismissType = {
                val value = navController.popBackStack()
                Timber.tag(TAG).i("onBackButtonClick() -> clicked\n\treturned value: $value")
            }

            ActivityFormScreen(
                id = id,
                isDarkTheme = isDarkTheme,
                onBackButtonClick = onBackButtonClick,
            )
        }
    }

}

private const val TAG = "che.AppNavigation"