package com.lanier.rocoguide.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.lanier.rocoguide.base.net.Net
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 14:39
 * Desc  :
 */
var defaultLocalJsonDataPath = ""
var defaultLocalApkDataPath = ""

fun InputStream.copyStreamToFile(outputFile: File) {
    this.use { input ->
        outputFile.outputStream().use { output ->
            val buffer = ByteArray(4 * 1024) // buffer size
            while (true) {
                val byteCount = input.read(buffer)
                if (byteCount < 0) break
                output.write(buffer, 0, byteCount)
            }
            output.flush()
        }
    }
}

sealed class DownloadStatus{
    data class Progress(val percent: Int): DownloadStatus()
    data class Complete(val success: Boolean, val file: File): DownloadStatus()
}

suspend fun downloadApk(url: String): Flow<DownloadStatus> = withContext(Dispatchers.IO){
    val responseBody = withContext(Dispatchers.Default) {
        Net.downloadService.getFileStream(url)
    }
    "download size -> ${responseBody.contentLength()}".logE()
    val path = File(defaultLocalApkDataPath)
    if (!path.exists()){
        path.mkdir()
    }
    val filename = url.substring(url.lastIndexOf("/") + 1, url.length)
    "download filename -> $filename".logE()
    val outputFile = File(defaultLocalApkDataPath, filename)
    if (outputFile.exists()) {
        outputFile.delete()
    }
    try {
        return@withContext responseBody.downloadFileWithProgress(outputFile)
    } catch (e: Exception) {
        "download err -> ${e.message}".logE()
    }
    emptyFlow()
}

private fun ResponseBody.downloadFileWithProgress(outputFile: File): Flow<DownloadStatus> = flow {
    emit(DownloadStatus.Progress(0))
    var success: Boolean
    kotlin.runCatching {
        byteStream().use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                val totalBytes = contentLength()
                val buffer = ByteArray(8 * 1024)
                var progressBytes = 0L
                while (true) {
                    val byteCount = inputStream.read(buffer)
                    if (byteCount == -1) break
                    outputStream.channel
                    outputStream.write(buffer, 0, byteCount)
                    progressBytes += byteCount
                    val percent = ((progressBytes * 100) / totalBytes).toInt()
                    emit(DownloadStatus.Progress(percent))
                }
                when {
                    progressBytes < totalBytes -> {
                        success = false
                        "download failed -> missing bytes".logE()
                    }
                    progressBytes > totalBytes -> {
                        success = false
                        "download failed -> too many bytes".logE()
                    }
                    else -> success = true
                }
            }
            emit(DownloadStatus.Complete(success, outputFile))
        }
    }.onFailure {
        "download failed -> ${it.message}".logE()
    }
}.flowOn(Dispatchers.IO).distinctUntilChanged()

fun Context.installApk(filename: String) {
    val file = File(defaultLocalApkDataPath, filename)
    if (file.exists()){
        installApk(file)
    }
}

fun Context.installApk(file: File){
    kotlin.runCatching {
        val contentUri = FileProvider.getUriForFile(this, "$packageName.provider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(contentUri, "application/vnd.android.package-archive")
        }
        startActivity(intent)
    }.onFailure {
        "failed -> ${it.message}".logE()
    }
}
