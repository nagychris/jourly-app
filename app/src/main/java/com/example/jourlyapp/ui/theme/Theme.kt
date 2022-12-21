package com.example.jourlyapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue100,
    onPrimary = Light80,

    secondary = Purple100,
    onSecondary = Light80,

    background = Color.White,
    onBackground = Dark80,
    surface = Light80,
    onSurface = Color.White,
)

// For now, light and dark scheme share the same color values
private val DarkColorScheme = darkColorScheme(
    primary = Blue100,
    onPrimary = Light80,

    secondary = Purple100,
    onSecondary = Light80,

    background = Color.White,
    onBackground = Dark80,
    surface = Light80,
    onSurface = Color.White,
)

@Composable
fun JourlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}