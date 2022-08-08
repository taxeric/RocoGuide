package com.lanier.rocoguide.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.repo.SpiritRepo
import com.lanier.rocoguide.vm.source.SpiritSource

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:08
 * Desc  :
 */
class SpiritViewModel: ViewModel() {

    private val repo = SpiritRepo()

    val spiritMainList get() = repo.getAllSpirit().cachedIn(viewModelScope)
}