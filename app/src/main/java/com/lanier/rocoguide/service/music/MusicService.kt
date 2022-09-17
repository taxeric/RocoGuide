package com.lanier.rocoguide.service.music

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.RemoteMusicEntity
import com.lanier.rocoguide.utils.logI

/**
 * Create by Eric
 * on 2022/9/12
 */
class MusicService : Service(){

    private lateinit var player: IMusicServiceInterface<RemoteMusicEntity>

    override fun onCreate() {
        super.onCreate()
        player = AudioPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.setPlayList(LocalCache.bgmList)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?) = null
}

class AudioPlayer: MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener,
    IMusicServiceInterface<RemoteMusicEntity>{

    private val player = MediaPlayer().apply {
        setAudioAttributes(AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build())
        setOnBufferingUpdateListener(this@AudioPlayer)
        setOnCompletionListener(this@AudioPlayer)
        setOnPreparedListener(this@AudioPlayer)
    }
    private val playList = mutableListOf<RemoteMusicEntity>()
    private var currentPlayIndex = -1

    override fun setPlayList(list: List<RemoteMusicEntity>){
        this.playList.clear()
        this.playList.addAll(list)
        this.currentPlayIndex = 0
        "set -> ${list.size}".logI()
        playCurrentIndexMusic()
    }

    fun addMusic(music: RemoteMusicEntity){
        playList.add(music)
    }

    override fun play(index: Int) {
        currentPlayIndex = if (index > playList.size - 1 || index < 0) {
             0
        } else index
        playCurrentIndexMusic()
    }

    private fun play(){
        if (currentPlayIndex != -1) {
            player.start()
        }
    }

    override fun pause(){
        player.pause()
    }

    override fun next(){
        if (playList.size != 1){
            if (currentPlayIndex == playList.size - 1){
                currentPlayIndex = 0
            } else {
                currentPlayIndex ++
            }
        }
        playCurrentIndexMusic()
    }

    override fun previous(){
        if (playList.size != 1) {
            if (currentPlayIndex == 0) {
                currentPlayIndex = playList.size - 1
            } else {
                currentPlayIndex --
            }
        }
        playCurrentIndexMusic()
    }

    override fun currentPlayComplete(autoPlayNext: Boolean) {
        if (autoPlayNext) {
            next()
        }
    }

    override fun stop(withRelease: Boolean){
        if (withRelease) {
            release()
        }
    }

    override fun release(){
        player.run {
            reset()
            release()
        }
    }

    override fun getCurrentPlayMusic(): RemoteMusicEntity?{
        if (playList.isEmpty()){
            return null
        }
        return playList[currentPlayIndex]
    }

    private fun playCurrentIndexMusic(){
        if (currentPlayIndex == -1) {
            return
        }
        val url = "${RetrofitHelper.baseUrl}${playList[currentPlayIndex].url}"
        "play -> $url".logI()
        playPrepare(url)
    }

    private fun playPrepare(url: String){
        player.run {
            reset()
            setDataSource(url)
            prepareAsync()
        }
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
    }

    override fun onCompletion(mp: MediaPlayer?) {
        currentPlayComplete()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        "play prepared complete".logI()
        play()
    }
}
