package com.budoxr.ett.ui.navigation

import androidx.annotation.StringRes
import com.budoxr.ett.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens (
    val route: String,
    @param:StringRes val titleResId: Int,
    val icon: ImageVector
) {

    val baseRoute: String
        get() = route.substringBefore('/')

    object MonitorScreen: Screens(route = "MonitorScreen", titleResId = R.string.title_monitor_screen, Icons.Filled.Timer)
    object ActivityScreen: Screens(route = "ActivityScreen", titleResId = R.string.title_activity_screen, Icons.Filled.Task)
    object ActivityFormScreen: Screens(route = "ActivityFormScreen/{id}", titleResId = R.string.title_activity_form_screen , Icons.AutoMirrored.Filled.Assignment
    )
}
