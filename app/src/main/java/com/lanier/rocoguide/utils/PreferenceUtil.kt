package com.lanier.rocoguide.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Create by Eric
 * on 2022/9/10
 */
object PreferenceUtil {

    lateinit var sharedPreferences: SharedPreferences
    private const val DEFAULT_SHARED_PREFERENCE_NAME = "RocoPreference"

    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences =
                context.getSharedPreferences(DEFAULT_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getInt(key: String, def: Int = -1) = sharedPreferences.getInt(key, def)
    fun getStr(key: String, def: String = "") = sharedPreferences.getString(key, def)?:def

    fun updateInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getWelcomeDialogVisible() = getInt(WELCOME_DIALOG, WELCOME_DIALOG_SHOW)
    fun getServeHost() = getStr(SERVE_HOST, "")
    fun getServePort() = getInt(SERVE_PORT, 0)

    fun getDynamicIconChoiceIndex() = getInt(DYNAMIC_ICON_INDEX, 0)
    fun updateDynamicIconIndex(index: Int) {
        updateInt(DYNAMIC_ICON_INDEX, index)
    }

    fun getRacialValue(): Int = getInt(RACIAL_SHOW_STYLE, RACIAL_GRID)
    fun getRacialStyle(style: Int = getRacialValue()): String {
        return when (style) {
            RACIAL_PROGRESS -> "进度"
            RACIAL_HEXAGONAL -> "六边形"
            else -> "表格"
        }
    }

    const val DARK_THEME = "dark_theme"

    const val RACIAL_SHOW_STYLE = "racial_show_style"
    const val RACIAL_GRID = 1
    const val RACIAL_PROGRESS = 2
    const val RACIAL_HEXAGONAL = 3

    const val WELCOME_DIALOG = "home_show_set_serve_dialog"
    const val WELCOME_DIALOG_SHOW = 1
    const val WELCOME_DIALOG_HIDE = 0

    const val SERVE_HOST = "serve_host"
    const val SERVE_PORT = "serve_port"

    private const val DYNAMIC_ICON_INDEX = "dynamic_icon_index"
}