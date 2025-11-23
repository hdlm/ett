package com.budoxr.ett.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = darkPrimaryBase,
    onPrimary = darkOnPrimaryBase,
    secondary = darkSecondaryBase,
    onSecondary = darkOnSecondaryBase,
    tertiary = darkTertiaryBase,
    background = darkBackground,
    surface = darkSurfaceBase,
    onSurface = darkOnSurfaceBase,
    onSurfaceVariant = grayLight,  //standard icon color
    error = darkError
)

private val LightColorScheme = lightColorScheme(
    primary = primaryBase,
    onPrimary = onPrimaryBase,
    secondary = secondaryBase,
    onSecondary = onSecondaryBase,
    tertiary = tertiaryBase,
    background = background,
    surface = surfaceBase,
    onSurface = onSurfaceBase,
    onSurfaceVariant = gray,  //standard icon color
    error = error
)

@Composable
fun EasyTimeTrackingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}