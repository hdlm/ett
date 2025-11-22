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
        Screens.ActivityFormScreen,
    )

    currentRoute?.let {
        val baseRoute = getBaseRoute(it)
        if ( baseRoute == Screens.MonitorScreen.baseRoute ||
            baseRoute == Screens.ActivityFormScreen.baseRoute
        ) {
            BottomAppBar(
                containerColor = grayLight,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                BottomNavigationBar(navController = navController, items = navigationItems)
            }
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
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.LightGray,
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = stringResource(R.string.content_description_icon) ) },
                label = { BottomNavigationBarText(selected = currentRoute == screen.route, label = stringResource(screen.titleResId) ) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (screen.route == Screens.MonitorScreen.route) {
                        val destination = Screens.MonitorScreen.baseRoute
                        navController.navigate(destination)
                    }
                    else if(screen.route == Screens.ActivityFormScreen.route) {
                        val screenName = Screens.ActivityFormScreen.baseRoute
                        val destination = "${screenName}/0"
                        navController.navigate(destination)
                    }
                    else {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = gray
                )
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