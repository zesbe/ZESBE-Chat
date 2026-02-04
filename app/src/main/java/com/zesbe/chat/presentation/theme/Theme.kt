package com.zesbe.chat.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ZESBE Brand Colors
private val ZesbeBlack = Color(0xFF0A0A0A)
private val ZesbeCharcoal = Color(0xFF1A1A1A)
private val ZesbeDarkGray = Color(0xFF2D2D2D)
private val ZesbeElectric = Color(0xFF00F0FF)
private val ZesbeNeonPink = Color(0xFFFF00E6)

private val DarkColorScheme = darkColorScheme(
    primary = ZesbeElectric,
    onPrimary = Color.Black,
    primaryContainer = ZesbeDarkGray,
    onPrimaryContainer = Color.White,
    secondary = ZesbeNeonPink,
    onSecondary = Color.White,
    background = ZesbeBlack,
    onBackground = Color.White,
    surface = ZesbeCharcoal,
    onSurface = Color.White,
    error = Color.Red,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006C8C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCDE6FF),
    onPrimaryContainer = Color(0xFF001E2B),
    secondary = ZesbeElectric,
    onSecondary = Color.Black,
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),
    error = Color.Red,
    onError = Color.White
)

@Composable
fun ZesbeChatTheme(
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
