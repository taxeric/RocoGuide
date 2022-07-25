package com.lanier.plugin_base

import android.app.Application

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 14:08
 * Desc  :
 */
open class PluginBaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(PluginActivityLifecycleCallback())
    }
}