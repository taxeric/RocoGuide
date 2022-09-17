package com.lanier.rocoguide.service.music

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.session.MediaSession
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.view.KeyEvent
import com.lanier.rocoguide.utils.NotificationUtil

/**
 * Create by Eric
 * on 2022/9/17
 */
class MusicServiceV2 : Service(){

    private lateinit var mediaSession: MediaSession
    private lateinit var mediaStyle: Notification.MediaStyle
    private lateinit var notificationManager: NotificationManager
    private val binder = MusicServiceBinder()

    private var musicController: IMusicController? = null
    private var isForegroundService = false

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mediaSession = MediaSession(this, "MediaPlayerSessionService").apply {
            setCallback(object : MediaSession.Callback(){
                override fun onMediaButtonEvent(mediaButtonIntent: Intent): Boolean {
                    if (Intent.ACTION_MEDIA_BUTTON == mediaButtonIntent.action) {
                        val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
                        } else mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)

                        event?.let {
                            when (it.keyCode) {
                                KeyEvent.KEYCODE_MEDIA_PLAY -> musicController?.resume()
                                KeyEvent.KEYCODE_MEDIA_PAUSE -> musicController?.pause()
                                else -> {}
                            }
                        }
                    }

                    return true
                }
            })
        }
        mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)
        startForeground(111, NotificationUtil.makeMusicNotification()).also {
            isForegroundService = true
        }
    }

    override fun onBind(intent: Intent?) = binder

    inner class MusicServiceBinder: Binder(){

        fun obtainService(): MusicServiceV2{
            return this@MusicServiceV2
        }
    }
}
