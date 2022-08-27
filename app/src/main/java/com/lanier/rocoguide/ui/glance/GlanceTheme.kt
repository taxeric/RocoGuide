package com.lanier.rocoguide.ui.glance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Create by Eric
 * on 2022/8/27
 */
internal val LocalLightColorProviders = staticCompositionLocalOf { dynamicThemeColorProviders() }
internal val LocalDarkColorProviders = staticCompositionLocalOf { dynamicThemeColorProviders(true) }

object GlanceTheme{
    val lightColors: ColorProviders
        @Composable
        @ReadOnlyComposable
        get() = LocalLightColorProviders.current

    val darkColor: ColorProviders
        @Composable
        @ReadOnlyComposable
        get() = LocalDarkColorProviders.current
}

fun dynamicThemeColorProviders(dark: Boolean = false): ColorProviders{
    return ColorProviders(
        primary = if (dark) Color(0xFFC2C1FF) else Color(0xFF4E4CCE),
        secondary = if (dark) Color(0xFF5ED4FC) else Color(0xFF006780),
        background = if (dark) Color(0xFF1C1B1F) else Color(0xFFFFFBFF),
        textColorPrimary = if (dark) Color(0xFFE5E1E6) else Color(0xFF1C1B1F)
    )
}

data class ColorProviders(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val textColorPrimary: Color,
)
