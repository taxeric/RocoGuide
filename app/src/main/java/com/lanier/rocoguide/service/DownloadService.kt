package com.lanier.rocoguide.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.utils.*
import kotlinx.coroutines.*

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/8 11:02
 * Desc  :
 */
class DownloadService: Service(), CoroutineScope by MainScope() {

    companion object{
        const val PARAMS_URL = "downloadUrl"
    }

    private var isDownloading = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.run {
            getStringExtra(PARAMS_URL) ?: ""
        }?: ""
        if (!isDownloading && url.isNotEmpty()) {
            "download start...".logI()
            launch {
                isDownloading = true
                LocalCache.newestData = LocalCache.newestData.copy(isDownloading = isDownloading,)
                downloadFile(url, onStartDownload = {
                    LocalCache.newestData = LocalCache.newestData.copy(
                        isDownloading = isDownloading,
                        filename = it
                    )
                }){
                    isDownloading = false
                    LocalCache.newestData = LocalCache.newestData.copy(
                        isDownloading = false,
                        hasDownloads = false,
                    )
                    NotificationUtil.errNotification(it.message?:"unknow err")
                    "download failed -> ${it.message}".logE()
                }.collect {
                    when {
                        it is DownloadStatus.Progress -> {
                            NotificationUtil.updateProgress(it.percent)
                        }
                        it is DownloadStatus.Complete -> {
                            "download success -> ${it.success}".logI()
                            isDownloading = false
                            LocalCache.newestData = LocalCache.newestData.copy(
                                isDownloading = isDownloading,
                                hasDownloads = it.success,
                            )
                            NotificationUtil.finishNotification()
                            if (it.success) {
                                applicationContext.installApk(LocalCache.newestData.filename)
                            }
                        }
                    }
                }
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
