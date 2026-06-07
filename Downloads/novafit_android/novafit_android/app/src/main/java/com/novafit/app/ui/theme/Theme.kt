package com.novafit.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = NovafitPurpleDark,
    surface = NovafitPurpleDark,
)

private val LightColorScheme = lightColorScheme(
    primary = NovafitPurple,
    onPrimary = NovafitWhite,
    primaryContainer = Purple80,
    onPrimaryContainer = NovafitPurpleDark,
    secondary = NovafitPurpleLight,
    onSecondary = NovafitWhite,
    background = NovafitBackground,
    onBackground = NovafitPurpleDark,
    surface = NovafitSurface,
    onSurface = NovafitPurpleDark,
    error = NovafitError,
)

@Composable
fun NovafitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
