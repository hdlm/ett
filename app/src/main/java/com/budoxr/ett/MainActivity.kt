/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.data.datastore.repositories.UserPreferencesRepository
import com.budoxr.ett.ui.navigation.AppNavigation
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import timber.log.Timber
import org.koin.compose.koinInject
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.extras?.let { extras ->
            for (key in extras.keySet()) {
                // Inlined to avoid "assigned value is never read" warning
                // Using get(key) and suppress deprecation to see all values regardless of type
                @Suppress("DEPRECATION")
                Timber.tag(TAG).d("Key: $key Value: ${extras.get(key)}")
            }
        }

        setContent {
            EasyTimeTrackingTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(isDarkTheme = isSystemInDarkTheme())
                }
            }
        }
    }

    companion object {
        const val TAG = "che.MainActivity"
    }
}

@Composable
fun MainScreen(isDarkTheme: Boolean) {
    val navController = rememberNavController()
    val userPreferencesRepository: UserPreferencesRepository = koinInject()
    
    // Fix: Determine start destination only once to avoid infinite navigation loops
    var startDestination by remember { mutableStateOf<Screens?>(null) }

    LaunchedEffect(Unit) {
        val lastRoute = userPreferencesRepository.lastScreen.first()
        startDestination = Screens.fromBaseRoute(lastRoute) ?: Screens.MonitorScreen
    }

    // Only render AppNavigation once the start destination is determined
    startDestination?.let { dest ->
        AppNavigation(
            navController = navController,
            isDarkTheme = isDarkTheme,
            startDest = dest
        )
    }
}
