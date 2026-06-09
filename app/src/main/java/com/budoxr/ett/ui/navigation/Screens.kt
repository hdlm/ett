/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import com.budoxr.ett.R

sealed class Screens (
    val route: String,
    @param:StringRes val titleResId: Int,
    val icon: ImageVector
) {

    val baseRoute: String
        get() = route.substringBefore('/')

    object MonitorScreen: Screens(route = "MonitorScreen", titleResId = R.string.title_monitor_screen, Icons.Outlined.Timer)
    object ActivityScreen: Screens(route = "ActivityScreen", titleResId = R.string.title_activity_screen, Icons.Outlined.Task)
    object ActivityFormScreen: Screens(route = "ActivityFormScreen/{id}", titleResId = R.string.title_activity_form_screen , Icons.AutoMirrored.Filled.Assignment)
    object ProgressChartScreen: Screens(route = "ProgressChartScreen", titleResId = R.string.title_progress_bar_chart_screen, Icons.Outlined.BarChart)
    object SettingScreen: Screens(route = "SettingScreen", titleResId = R.string.title_setting_screen, Icons.Outlined.Construction)
    object ManageBackupScreen: Screens(route = "ManageBackupScreen/{typeOperation}", titleResId = R.string.title_manage_backup_make, Icons.Outlined.Save)
    object AboutScreen: Screens(route = "AboutScreen", titleResId = R.string.title_about_screen, Icons.Outlined.Info)

    companion object {
        // Caches the sub-objects into an O(1) memory map on class loading phase
        private val routeMap: Map<String, Screens> by lazy {
            Screens::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .associateBy { it.baseRoute }
        }

        /**
         * Safely extracts the companion 'Screens' object matching the provided raw string route.
         * Returns null if no match is found, preventing sudden application crashes.
         */
        fun fromBaseRoute(baseRoute: String?): Screens? {
            if (baseRoute == null) return null
            return routeMap[baseRoute]
        }
    }
}
