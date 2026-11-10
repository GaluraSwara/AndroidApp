package com.galuraswara.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = WoodPrimary,
    secondary = BrassAccent,
    background = CreamBackground,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = WoodDark,
    onBackground = TextDark,
    onSurface = TextDark
)

private val DarkColorScheme = darkColorScheme(
    primary = BrassAccent,
    secondary = BrassLight,
    background = WoodDark,
    surface = Color(0xFF1E110A),
    onPrimary = WoodDark,
    onSecondary = WoodDark,
    onBackground = CreamBackground,
    onSurface = CreamBackground
)

@Composable
fun GaluraSwaraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
