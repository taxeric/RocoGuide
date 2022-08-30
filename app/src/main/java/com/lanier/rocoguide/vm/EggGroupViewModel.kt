package com.lanier.rocoguide.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.source.EggGroupSource

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/30 10:54
 * Desc  :
 */
class EggGroupViewModel: ViewModel() {

    val eggGroupFlow = Pager(
        PagingConfig(20, prefetchDistance = 1, enablePlaceholders = false)
    ){
        EggGroupSource()
    }.flow.cachedIn(viewModelScope)
}