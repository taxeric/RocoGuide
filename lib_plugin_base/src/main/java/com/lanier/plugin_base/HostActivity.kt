package com.lanier.plugin_base

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 11:38
 * Desc  :
 */
class HostActivity: ComponentActivity() {

    var pluginActivity: IPluginActivityInterface? = null

    companion object {
        val ARG_PLUGIN_CLASS_NAME = "ARG_PLUGIN_CLASS_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(ARG_PLUGIN_CLASS_NAME)?.let {
            val clazz= pluginClassLoader.loadClass(it)
            pluginActivity = clazz.newInstance() as? IPluginActivityInterface
            pluginActivity?.registerHostActivity(this)
        }
        pluginActivity?.lifecycle = this.lifecycle
        pluginActivity?.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginActivity?.onDestroy()
    }
}