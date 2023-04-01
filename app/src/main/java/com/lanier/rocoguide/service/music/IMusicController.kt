package com.lanier.rocoguide.service.music

import androidx.compose.runtime.compositionLocalOf
import com.lanier.rocoguide.entity.MusicEntity

/**
 * Create by Eric
 * on 2022/9/17
 */
interface IMusicController {

    fun setPlayList(music: MusicEntity)
    fun play(music: MusicEntity)
    fun resume()
    fun pause()
    fun stop(withRelease: Boolean = false)
    fun release()
}
val LocalSongController = compositionLocalOf<IMusicController?> { null }
