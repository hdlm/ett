package com.budoxr.ett.ui.components

import com.budoxr.ett.R
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
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
fun MainBottomBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)

    val navigationItems = listOf(
        Screens.MonitorScreen,
        Screens.ActivityScreen,
    )

    currentRoute?.let {
        BottomAppBar {
            BottomNavigationBar(navController = navController, items = navigationItems)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screens>,
) {

    val currentRoute = currentRoute(navController)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = stringResource(R.string.content_description_icon) ) },
                label = { BottomNavigationBarText(selected = currentRoute == screen.route, label = stringResource(screen.titleResId) ) },
                selected = currentRoute == screen.route,
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