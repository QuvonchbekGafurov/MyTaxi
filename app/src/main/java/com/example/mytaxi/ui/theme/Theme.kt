package com.example.mytaxi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

private val DarkColorPalette = darkColorScheme(
    primary = DarkContentPrimary,
    onPrimary = DarkBackgroundPrimary,
    secondary = DarkContentSecondary,
    onSecondary = DarkOutlineSecondary,
    background = DarkBackgroundPrimary,
    onBackground = DarkContentPrimary,
    surface = DarkBackgroundSecondary,
    onSurface = DarkContentPrimary,
    primaryContainer = DarkButtonPrimary2Default,
    onPrimaryContainer = DarkIconPrimary,
    secondaryContainer = DarkContentTertiary,
    onSecondaryContainer = DarkIconTertiary,
    tertiary = DarkContentColorfulAccent,
    onTertiary = DarkOutlineOthersImmutableDark,
    error = Color.Red, // example error color
    onError = Color.White, // example on error color
    errorContainer = Color.Red.copy(alpha = 0.1f), // example error container color
    onErrorContainer = Color.Red.copy(alpha = 0.9f), // example on error container color
    outline = DarkOutlineSecondary,
    outlineVariant = DarkOutlineOthersPure,
    scrim = Color.Black.copy(alpha = 0.5f) // example scrim color
)

private val LightColorPalette = androidx.compose.material3.lightColorScheme(
    primary = LightContentPrimary,
    onPrimary = LightBackgroundPrimary,
    secondary = LightContentSecondary,
    onSecondary = LightOutlineSecondary,
    background = LightBackgroundPrimary,
    onBackground = LightContentPrimary,
    surface = LightBackgroundSecondary,
    onSurface = LightContentPrimary,
    primaryContainer = LightButtonPrimary2Default,
    onPrimaryContainer = LightIconPrimary,
    secondaryContainer = LightContentTertiary,
    onSecondaryContainer = LightIconTertiary,
    tertiary = LightContentColorfulAccent,
    onTertiary = LightOutlineOthersImmutableDark,
    error = Color.Red, // example error color
    onError = Color.White, // example on error color
    errorContainer = Color.Red.copy(alpha = 0.1f), // example error container color
    onErrorContainer = Color.Red.copy(alpha = 0.9f), // example on error container color
    outline = LightOutlineSecondary,
    outlineVariant = LightOutlineOthersPure,
    scrim = Color.Black.copy(alpha = 0.5f) // exam
)
@Composable
fun MyTaxiTheme (useDarkTheme: Boolean = isSystemInDarkTheme(),
content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
