package com.example.jourlyapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue100,
    onPrimary = Light,

    secondary = Purple100,
    onSecondary = Light,

    background = Color.White,
    onBackground = TextRegular,
    surface = Light,
    onSurface = TextRegular,

    error = Danger100,
    onError = Light,
    errorContainer = Danger80,
    onErrorContainer = Light,
)

@Composable
fun JourlyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}