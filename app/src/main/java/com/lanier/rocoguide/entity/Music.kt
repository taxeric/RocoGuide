package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/9/17
 */
data class MusicEntity(
    val path: String = "",
    val name: String = "",
    val duration: Long = 0L
)

enum class MusicAction{
    PAUSE,
    RESUME,
    STOP,

    NOTHING
}
