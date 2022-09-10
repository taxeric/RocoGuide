package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.lanier.rocoguide.utils.PreferenceUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Create by Eric
 * on 2022/9/10
 */
object SettingsHelper {
    private lateinit var appDarkThemeFlow: MutableStateFlow<AppSetting>

    init {
        if (!::appDarkThemeFlow.isInitialized) {
            appDarkThemeFlow = MutableStateFlow(
                AppSetting(
                    PreferenceDarkTheme(
                        PreferenceUtil.getInt(
                            PreferenceUtil.DARK_THEME,
                            1
                        )
                    )
                )
            )
        }
    }
    val composableDarkThemeFlow = appDarkThemeFlow.asStateFlow()

    fun switchDarkThemeMode(mode: Int) {
        appDarkThemeFlow.update {
            it.copy(appDarkTheme = PreferenceDarkTheme(darkThemeValue = mode))
        }
        PreferenceUtil.sharedPreferences.edit().putInt(PreferenceUtil.DARK_THEME, mode).apply()
    }

    data class AppSetting(
        val appDarkTheme: PreferenceDarkTheme = PreferenceDarkTheme()
    )

    class PreferenceDarkTheme(var darkThemeValue: Int = FOLLOW_SYSTEM) {
        companion object {
            const val FOLLOW_SYSTEM = 1
            const val ON = 2
            const val OFF = 3
        }

        @Composable
        fun isDarkTheme(): Boolean {
            return if (darkThemeValue == FOLLOW_SYSTEM) {
                isSystemInDarkTheme()
            } else {
                darkThemeValue == ON
            }
        }
    }
}
