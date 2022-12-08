package com.lanier.rocoguide.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.lanier.rocoguide.ui.common.SettingsHelper

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val GuideLightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
)


private val GuideDarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
)

// <editor-fold defaultstate="collapsed" desc="扩展颜色">
@Immutable
data class ExtendColors(
    val defaultMainBackground: Color,
    val defaultLazyItemBackground: Color,
    val defaultRacialGridBgColor: Color,
    val defaultRacialValueColor: Color,
    val defaultPowerTvValueColor: Color,
    val defaultAttackTvValueColor: Color,
    val defaultDefenseTvValueColor: Color,
    val defaultMagicAttackTvValueColor: Color,
    val defaultMagicDefenseTvValueColor: Color,
    val defaultSpeedTvValueColor: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendColors(
        defaultMainBackground = Color.Unspecified,
        defaultLazyItemBackground = Color.Unspecified,
        defaultRacialGridBgColor = Color.Unspecified,
        defaultRacialValueColor = Color.Unspecified,
        defaultPowerTvValueColor = Color.Unspecified,
        defaultAttackTvValueColor = Color.Unspecified,
        defaultDefenseTvValueColor = Color.Unspecified,
        defaultMagicAttackTvValueColor = Color.Unspecified,
        defaultMagicDefenseTvValueColor = Color.Unspecified,
        defaultSpeedTvValueColor = Color.Unspecified,
    )
}

private val localExtendLightColors = ExtendColors(
    defaultMainBackground = local_default_main_background_light,
    defaultLazyItemBackground = local_default_lazy_item_light,
    defaultRacialGridBgColor = local_default_racial_grid_bg_light,
    defaultRacialValueColor = local_default_racial_value_light,
    defaultPowerTvValueColor = local_default_power_tv_value_light,
    defaultAttackTvValueColor = local_default_attack_tv_value_light,
    defaultDefenseTvValueColor = local_default_defense_tv_value_light,
    defaultMagicAttackTvValueColor = local_default_magic_attack_tv_value_light,
    defaultMagicDefenseTvValueColor = local_default_magic_defense_tv_value_light,
    defaultSpeedTvValueColor = local_default_speed_tv_value_light,
)
private val localExtendDarkColors = ExtendColors(
    defaultMainBackground = local_default_main_background_dark,
    defaultLazyItemBackground = local_default_lazy_item_dark,
    defaultRacialGridBgColor = local_default_racial_grid_bg_dark,
    defaultRacialValueColor = local_default_racial_value_dark,
    defaultPowerTvValueColor = local_default_power_tv_value_dark,
    defaultAttackTvValueColor = local_default_attack_tv_value_dark,
    defaultDefenseTvValueColor = local_default_defense_tv_value_dark,
    defaultMagicAttackTvValueColor = local_default_magic_attack_tv_value_dark,
    defaultMagicDefenseTvValueColor = local_default_magic_defense_tv_value_dark,
    defaultSpeedTvValueColor = local_default_speed_tv_value_dark,
)
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="首选项-暗色模式">
val LocalDarkTheme = compositionLocalOf { SettingsHelper.PreferenceDarkTheme() }
// </editor-fold>

fun Color.applyOpacity(enabled: Boolean): Color {
    return if (enabled) this else this.copy(alpha = 0.62f)
}

@Composable
fun SettingsProvider(content: @Composable () -> Unit){
    val appSettings = SettingsHelper.composableDarkThemeFlow.collectAsState().value
//    "app settings -> ${appSettings.appDarkTheme.darkThemeValue}".logI()
    CompositionLocalProvider(
        LocalDarkTheme provides appSettings.appDarkTheme
    ) {
        content()
    }
}

@Composable
fun RocoGuideTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        GuideLightColors
    } else {
        GuideDarkColors
    }

    val localExtColor = if (useDarkTheme)
        localExtendDarkColors
    else
        localExtendLightColors

    CompositionLocalProvider(
        LocalExtendedColors provides localExtColor,
    ) {
        MaterialTheme(
            colorScheme = colors,
            content = content
        )
    }
}

object ExtendedTheme{
    val colors: ExtendColors
        @Composable
        get() = LocalExtendedColors.current
}

/*
@Composable
fun RocoGuideTheme(
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
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}*/
