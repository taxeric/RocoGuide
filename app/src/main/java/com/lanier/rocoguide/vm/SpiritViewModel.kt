package com.lanier.rocoguide.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.repo.SpiritRepo

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:08
 * Desc  :
 */
class SpiritViewModel: ViewModel() {

    private val repo = SpiritRepo()

    val spiritMainListFlow = repo.getAllSpirit().cachedIn(viewModelScope)
}