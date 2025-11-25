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
import com.budoxr.ett.ui.ActivityFormScreen
import com.budoxr.ett.ui.ActivityScreen
import com.budoxr.ett.ui.MonitorScreen
import timber.log.Timber


@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    startDest: Screens,
) {
    val navigateToActivity: onDismissType = {
        val destination = Screens.ActivityScreen.baseRoute
        navController.navigate(destination)
    }
    val navigateToActivityForm: onIntType = {
        Timber.tag(TAG).i("navigateToActivityForm() -> called, id: $it")
        val screenName = Screens.ActivityFormScreen.baseRoute
        val destination = "${screenName}/0"
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