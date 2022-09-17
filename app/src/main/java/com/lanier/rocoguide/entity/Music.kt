package com.lanier.rocoguide.entity

import androidx.compose.runtime.compositionLocalOf

/**
 * Create by Eric
 * on 2022/9/17
 */
data class RemoteMusicEntity(
    val url: String,
    val name: String,
    val duration: Long = 0L
)

data class MusicState(
    val isPlaying: Boolean = false
)

val LocalMusicState = compositionLocalOf { MusicState() }

enum class MusicAction{
    PAUSE,
    RESUME,
    STOP,

    NOTHING
}
