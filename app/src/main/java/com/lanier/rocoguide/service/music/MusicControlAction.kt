package com.lanier.rocoguide.service.music

import com.lanier.rocoguide.entity.MusicEntity

/**
 * Create by Eric
 * on 2023/4/1
 */
sealed interface MusicControlAction {

    data class Play(val song: MusicEntity) : MusicControlAction
    object Pause: MusicControlAction
    object Resume: MusicControlAction
    object Stop: MusicControlAction
}