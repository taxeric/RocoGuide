package com.lanier.plugin_base

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 11:39
 * Desc  :
 */
object PluginLoader {
    fun loadPlugin(context: Context) {
        val inputStream = context.assets.open("plugin.apk")
        val filesDir = context.externalCacheDir
        val apkFile = File(filesDir?.absolutePath, "plugin.apk")
        apkFile.writeBytes(inputStream.readBytes())

        val dexFile = File(filesDir, "dex")
        if (!dexFile.exists()){
            dexFile.createNewFile()
        }
        "输出dex路径${dexFile}".logI()
        pluginClassLoader = DexClassLoader(apkFile.absolutePath, dexFile.absolutePath, null, this.javaClass.classLoader)
        "load complete".logI()
    }
}

lateinit var pluginClassLoader: DexClassLoader