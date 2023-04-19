package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/9/17
 */
data class MusicEntity(
    val path: String = "",
    val name: String = "",
    val duration: Long = 0L,
    val downloadState: DownloadState = DownloadState.Download
)

sealed interface DownloadState {
    object DownloadDone: DownloadState
    object Downloading: DownloadState
    object Download: DownloadState
}

enum class MusicAction{
    PAUSE,
    RESUME,
    STOP,

    NOTHING
}
