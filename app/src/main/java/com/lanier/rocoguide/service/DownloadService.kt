package com.lanier.rocoguide.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.lanier.rocoguide.base.cache.CurrentDownloadContent
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
                downloadContentPrepared()
                downloadFile(url, onStartDownload = {
                    downloadStarted(it)
                }){
                    isDownloading = false
                    downloadFailed(it)
                    "download failed -> ${it.message}".logE()
                }.collect {
                    when {
                        it is DownloadStatus.Progress -> {
                            downloading(it)
                        }
                        it is DownloadStatus.Complete -> {
                            "download success -> ${it.success}".logI()
                            isDownloading = false
                            downloadCompleted(it)
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

    private fun downloadContentPrepared(){
        when(LocalCache.currentDownloadContent.value) {
            is CurrentDownloadContent.APK -> {}
            is CurrentDownloadContent.UpdateApk -> {
                LocalCache.newestData = LocalCache.newestData.copy(isDownloading = isDownloading,)
            }
            else -> {}
        }
    }

    private fun downloadStarted(filename: String = "") {
        when(LocalCache.currentDownloadContent.value) {
            is CurrentDownloadContent.APK -> {}
            is CurrentDownloadContent.UpdateApk -> {
                LocalCache.newestData = LocalCache.newestData.copy(
                    isDownloading = isDownloading,
                    filename = filename
                )
            }
            else -> {}
        }
    }

    private fun downloadFailed(throwable: Throwable) {
        when(LocalCache.currentDownloadContent.value) {
            is CurrentDownloadContent.APK -> {}
            is CurrentDownloadContent.UpdateApk -> {
                LocalCache.newestData = LocalCache.newestData.copy(
                    isDownloading = false,
                    hasDownloads = false,
                )
                NotificationUtil.errNotification(throwable.message ?: "unknow err")
            }
            else -> {}
        }
    }

    private fun downloading(progress: DownloadStatus.Progress){
        when(LocalCache.currentDownloadContent.value) {
            is CurrentDownloadContent.APK -> {}
            is CurrentDownloadContent.UpdateApk -> {
                NotificationUtil.updateProgress(progress.percent)
            }
            else -> {}
        }
    }

    private fun downloadCompleted(complete: DownloadStatus.Complete){
        when(LocalCache.currentDownloadContent.value) {
            is CurrentDownloadContent.APK -> {}
            is CurrentDownloadContent.UpdateApk -> {
                LocalCache.newestData = LocalCache.newestData.copy(
                    isDownloading = isDownloading,
                    hasDownloads = complete.success,
                )
                NotificationUtil.finishNotification()
                if (complete.success) {
                    applicationContext.installApk(LocalCache.newestData.filename)
                }
            }
            else -> {}
        }
    }
}
