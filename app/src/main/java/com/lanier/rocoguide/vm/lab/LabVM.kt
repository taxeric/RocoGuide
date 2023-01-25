package com.lanier.rocoguide.vm.lab

import androidx.lifecycle.ViewModel
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.utils.PreferenceUtil
import com.lanier.rocoguide.utils.logE

/**
 * Create by Eric
 * on 2023/1/25
 */
class LabVM: ViewModel() {

    private var lastChoiceIndex = -1

    fun readIconConfig() {
        lastChoiceIndex = PreferenceUtil.getDynamicIconChoiceIndex()
        if (lastChoiceIndex < 0) {
            lastChoiceIndex = 0
        }
        LocalCache.dynamicIcons[lastChoiceIndex] =
            LocalCache.dynamicIcons[lastChoiceIndex].copy(selected = true)
    }

    fun writeIconConfig(index: Int) {
        if (index < 0) {
            return
        }
        PreferenceUtil.updateDynamicIconIndex(index)
        LocalCache.dynamicIcons[lastChoiceIndex] =
            LocalCache.dynamicIcons[lastChoiceIndex].copy(selected = false)
        LocalCache.dynamicIcons[index] =
            LocalCache.dynamicIcons[index].copy(selected = true)
        lastChoiceIndex = index
    }
}