package com.budoxr.ett.ui.navigation

import androidx.annotation.StringRes
import com.budoxr.ett.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens (
    val route: String,
    @param:StringRes val titleResId: Int,
    val icon: ImageVector
) {

    val baseRoute: String
        get() = route.substringBefore('/')

    object MonitorScreen: Screens(route = "MonitorScreen", titleResId = R.string.title_monitor_screen, Icons.Filled.Home)
    object ActivityFormScreen: Screens(route = "ActivityFormScreen/{id}", titleResId = R.string.title_activity_form_screen , Icons.Filled.Home)
}
