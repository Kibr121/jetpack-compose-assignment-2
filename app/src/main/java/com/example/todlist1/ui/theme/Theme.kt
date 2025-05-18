package com.example.todlist1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Secondary.copy(alpha = 0.12f),
    onSecondaryContainer = OnSecondary,
    tertiary = Info,
    onTertiary = OnPrimary,
    tertiaryContainer = Info.copy(alpha = 0.12f),
    onTertiaryContainer = OnPrimary,
    error = Error,
    onError = OnError,
    errorContainer = Error.copy(alpha = 0.12f),
    onErrorContainer = OnError,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = Surface.copy(alpha = 0.8f),
    onSurfaceVariant = OnSurface.copy(alpha = 0.7f),
    outline = Divider,
    outlineVariant = Divider.copy(alpha = 0.5f),
    scrim = OnBackground.copy(alpha = 0.32f),
    inverseSurface = OnBackground,
    inverseOnSurface = Background,
    inversePrimary = Primary.copy(alpha = 0.8f)
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary.copy(alpha = 0.8f),
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary.copy(alpha = 0.8f),
    onSecondary = OnSecondary,
    secondaryContainer = Secondary.copy(alpha = 0.12f),
    onSecondaryContainer = OnSecondary,
    tertiary = Info.copy(alpha = 0.8f),
    onTertiary = OnPrimary,
    tertiaryContainer = Info.copy(alpha = 0.12f),
    onTertiaryContainer = OnPrimary,
    error = Error.copy(alpha = 0.8f),
    onError = OnError,
    errorContainer = Error.copy(alpha = 0.12f),
    onErrorContainer = OnError,
    background = OnBackground,
    onBackground = Background,
    surface = OnSurface,
    onSurface = Surface,
    surfaceVariant = OnSurface.copy(alpha = 0.8f),
    onSurfaceVariant = Surface.copy(alpha = 0.7f),
    outline = Divider,
    outlineVariant = Divider.copy(alpha = 0.5f),
    scrim = OnBackground.copy(alpha = 0.32f),
    inverseSurface = Background,
    inverseOnSurface = OnBackground,
    inversePrimary = Primary.copy(alpha = 0.8f)
)

@Composable
fun Todlist1Theme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}