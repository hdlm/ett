package com.budoxr.ett

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.budoxr.ett.di.Modules.appModule
import com.budoxr.ett.di.Modules.databaseModule
import com.budoxr.ett.ui.navigation.AppNavigation
import com.budoxr.ett.ui.navigation.Screens
import com.budoxr.ett.ui.theme.EasyTimeTrackingTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException
import timber.log.Timber
import timber.log.Timber.DebugTree

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

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Timber.tag(TAG).d("Debug mode enabled")
        } else {
            Timber.tag(TAG).d("Release mode disabled")
        }

        try {
            startKoin {
                androidContext(this@MainActivity)
                androidLogger()
                modules(appModule, databaseModule)
            }
        } catch (ignore: KoinApplicationAlreadyStartedException) {
            // ignore
        }

        setContent {
            EasyTimeTrackingTheme(dynamicColor = false) {
                MainScreen(isDarkTheme = isSystemInDarkTheme())
            }
        }
    }
    companion object {
        const val TAG = "che.MainActivity"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainScreen(isDarkTheme: Boolean ) {
    val navController = rememberNavController()

    AppNavigation(
        navController = navController,
        isDarkTheme = isDarkTheme,
        startDest = Screens.ActivityFormScreen
    )

}