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
    fun getStr(key: String, def: String = "") = sharedPreferences.getString(key, def)

    fun updateInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getRacialValue(): Int = getInt(RACIAL_SHOW_STYLE, 1)
    fun getRacialStyle(style: Int = getRacialValue()): String {
        return when (style) {
            2 -> "进度"
            else -> "表格"
        }
    }

    const val DARK_THEME = "dark_theme"

    const val RACIAL_SHOW_STYLE = "racial_show_style"
    const val RACIAL_GRID = 1
    const val RACIAL_PROGRESS = 2
}