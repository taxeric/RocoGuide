package com.lanier.plugin_base

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.io.InputStream

/**
 * Author: Eric
 * Email : 1248389474@qq.com
 * Date  : 2022/7/25 11:39
 * Desc  :
 */
object PluginLoader {

    fun loadPluginApk(
        filepath: String,
        filename: String,
        loadResult: (Boolean, String) -> Unit = {_,_ ->}
    ){
        val apkFile = File(filepath, filename)
        if (!apkFile.exists()) {
            loadResult(false, "File not found")
            return
        }
        try {
            val dexFile = File(filepath, "${filename}_dex")
            if (!dexFile.exists()){
                dexFile.createNewFile()
            }
            pluginClassLoader = DexClassLoader(apkFile.absolutePath, dexFile.absolutePath, null, this.javaClass.classLoader)
            loadResult(true, "success")
        } catch (e: Exception) {
            loadResult(false, "failed -> ${e.message}")
        }
    }

    fun loadAssetsPluginApk(context: Context, filename: String, loadResult: (Boolean, String) -> Unit = {_,_ ->}) {
        val inputStream = context.assets.open(filename)
        val filesDir = context.externalCacheDir
        val apkFile = File(filesDir?.absolutePath, filename)
        if (!apkFile.exists()) {
            apkFile.writeBytes(inputStream.readBytes())
        }
        val dexFile = File(filesDir, "${filename}_dex")
        if (!dexFile.exists()){
            dexFile.createNewFile()
        }
        try {
            "输出dex路径${dexFile}".logI()
            pluginClassLoader = DexClassLoader(apkFile.absolutePath, dexFile.absolutePath, null, this.javaClass.classLoader)
            loadResult(true, "success")
        } catch (e: Exception) {
            loadResult(false, "failed -> ${e.message}")
        }
    }
}

lateinit var pluginClassLoader: DexClassLoader