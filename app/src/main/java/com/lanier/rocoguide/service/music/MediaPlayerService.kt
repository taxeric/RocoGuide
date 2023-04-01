package com.lanier.rocoguide.service.music

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.lanier.rocoguide.entity.MusicAction

/**
 * Create by Eric
 * on 2023/4/1
 */
class MediaPlayerService: Service() {

    var songController: IMusicController? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (MusicAction.values()[intent?.action?.toInt() ?: MusicAction.NOTHING.ordinal]) {
            MusicAction.PAUSE -> songController?.pause()
            MusicAction.RESUME -> songController?.resume()
            MusicAction.STOP -> songController?.stop()
            MusicAction.NOTHING -> {}
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}