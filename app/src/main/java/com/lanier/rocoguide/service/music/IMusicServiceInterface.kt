package com.lanier.rocoguide.service.music

import com.lanier.rocoguide.entity.MusicEntity

/**
 * Create by Eric
 * on 2022/9/17
 */
interface IMusicServiceInterface<T> {

    fun setPlayList(list: List<T>)
    fun play(index: Int = 0)
    fun pause()
    fun stop(withRelease: Boolean = false)
    fun next()
    fun previous()
    fun currentPlayComplete(autoPlayNext: Boolean = false)
    fun getCurrentPlayMusic(): MusicEntity?
    fun release()
}