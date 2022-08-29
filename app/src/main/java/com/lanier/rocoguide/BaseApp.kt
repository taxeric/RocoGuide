package com.lanier.rocoguide

import com.lanier.lib_net.RetrofitHelper
import com.lanier.plugin_base.PluginBaseApp
import com.lanier.rocoguide.utils.defaultLocalJsonDataPath

/**
 * Create by Eric
 * on 2022/7/25
 */
class BaseApp: PluginBaseApp() {

    override fun onCreate() {
        super.onCreate()
        defaultLocalJsonDataPath = externalCacheDir!!.absolutePath
        RetrofitHelper.initHelper("")
    }
}
