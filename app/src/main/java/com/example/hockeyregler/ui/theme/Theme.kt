package com.example.hockeyregler.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Ljust tema inspirerat av Domarakademin
private val LightColorScheme = lightColorScheme(
    primary = OrangeRed,           // Röd/orange för knappar och accenter
    secondary = DeepBlue,          // Mörk blå för sekundära element
    tertiary = LightBlue,          // Ljusare blå
    background = OffWhite,         // Nästan vit bakgrund
    surface = White,               // Vita kort
    onPrimary = White,             // Vit text på röd
    onSecondary = White,           // Vit text på blå
    onBackground = DarkGray,       // Mörk text på ljus bakgrund
    onSurface = DarkGray,          // Mörk text på vita kort
    error = Error,
    onError = White
)

// Mörkt tema (om användaren föredrar det)
private val DarkColorScheme = darkColorScheme(
    primary = OrangeRed,
    secondary = LightBlue,
    tertiary = LightOrange,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = White,
    onSecondary = White,
    onBackground = OffWhite,
    onSurface = OffWhite,
    error = Error,
    onError = White
)

@Composable
fun HockeyReglerTheme(
    darkTheme: Boolean = false,  // Ljust tema som standard
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}