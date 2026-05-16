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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.ui.navigation.AppNavigation
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras?.getString(key)  // fix the deprecated warning of the line above
                Timber.tag(TAG).d("Key: $key Value: $value")
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
fun MainScreen(isDarkTheme: Boolean ) {
    val navController = rememberNavController()

    AppNavigation(
        navController = navController,
        isDarkTheme = isDarkTheme,
        startDest = Screens.MonitorScreen
    )

}