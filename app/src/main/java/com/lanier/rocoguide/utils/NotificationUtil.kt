package com.lanier.rocoguide.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.lanier.rocoguide.R

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/9/8 13:54
 * Desc  :
 */
@SuppressLint("StaticFieldLeak")
object NotificationUtil{

    private const val defaultChannelName = "VersionUpdate"
    private const val defaultChannelDesc = "用于更新版本的通知显示"
    private const val CHANNEL_ID = "rocoChannelId"
    private const val NOTIFICATION_ID = 101

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    private lateinit var context: Context

    fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, defaultChannelName, importance).apply {
            description = defaultChannelDesc
        }
        this.context = context
        notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun makeNotification(){
        notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_gulu_base_bg_1)
            .setContentTitle("RocoGuide")
            .setContentText("正在下载")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notifyNotification()
    }

    fun updateProgress(progress: Int) {
        notificationBuilder.setProgress(100, progress, false)
        notifyNotification()
    }

    fun finishNotification() {
        notificationBuilder
            .setContentText("下载完成")
            .setProgress(0, 0, false)
            .setAutoCancel(true)
        notifyNotification(true)
    }

    fun errNotification(msg: String) {
        notificationBuilder
            .setContentText("下载失败: $msg\n请点击[关于]重试")
            .setProgress(0, 0, false)
            .addAction(R.drawable.ic_gulu_base_bg_1, "确定", null)
            .setAutoCancel(true)
        notifyNotification()
    }

    fun cancelNotification(){
        notifyNotification(true)
    }

    private fun notifyNotification(cancel: Boolean = false){
        if (cancel) {
            notificationManager.cancel(NOTIFICATION_ID)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}
