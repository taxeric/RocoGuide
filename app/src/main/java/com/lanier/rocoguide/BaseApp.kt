package com.lanier.rocoguide

import android.app.Application
import android.os.Environment
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.utils.NotificationUtil
import com.lanier.rocoguide.utils.PreferenceUtil
import com.lanier.rocoguide.utils.defaultLocalApkDataPath
import com.lanier.rocoguide.utils.defaultLocalJsonDataPath

/**
 * Create by Eric
 * on 2022/7/25
 */
class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceUtil.init(this)
        val baseCacheDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
        defaultLocalJsonDataPath = "$baseCacheDir/html/"
        defaultLocalApkDataPath = "$baseCacheDir/apk/"
        NotificationUtil.createNotificationChannel(this)
        RetrofitHelper.initHelper(LocalCache.BASE_URL)
    }
}
