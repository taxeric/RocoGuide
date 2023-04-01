package com.lanier.rocoguide.vm.music

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lanier.rocoguide.service.music.MusicEnvironment

/**
 * Create by Eric
 * on 2023/4/1
 */
class MusicVMFactory (
    private val context: Context
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicViewModel(MusicEnvironment(context = context)) as T
    }
}