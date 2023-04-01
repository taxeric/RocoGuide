package com.lanier.rocoguide.vm.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanier.rocoguide.service.music.MusicControlAction
import com.lanier.rocoguide.service.music.MusicEnvironment
import kotlinx.coroutines.launch

/**
 * Create by Eric
 * on 2023/4/1
 */
class MusicViewModel (private val environment: MusicEnvironment): ViewModel() {

    fun dispatch(action: MusicControlAction) {
        when (action) {
            MusicControlAction.Pause -> {
                viewModelScope.launch {
                    environment.pause()
                }
            }
            is MusicControlAction.Play -> {
                viewModelScope.launch {
                    environment.play(action.song)
                }
            }
            MusicControlAction.Resume -> {
                viewModelScope.launch {
                    environment.resume()
                }
            }
            MusicControlAction.Stop -> {
                viewModelScope.launch {
                    environment.stop()
                }
            }
        }
    }
}