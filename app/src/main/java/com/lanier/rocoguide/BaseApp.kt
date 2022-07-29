package com.lanier.rocoguide

import com.lanier.lib_net.RetrofitHelper
import com.lanier.plugin_base.PluginBaseApp

/**
 * Create by Eric
 * on 2022/7/25
 */
class BaseApp: PluginBaseApp() {

    override fun onCreate() {
        super.onCreate()
        RetrofitHelper.initHelper("")
    }
}
