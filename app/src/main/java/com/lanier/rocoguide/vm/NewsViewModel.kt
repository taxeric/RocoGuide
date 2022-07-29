package com.lanier.rocoguide.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.source.NewsSource

/**
 * Create by Eric
 * on 2022/7/29
 */
class NewsViewModel: ViewModel() {

    private val _newsFlow = Pager(
        PagingConfig(5, prefetchDistance = 1, enablePlaceholders = false)
    ){
        NewsSource()
    }.flow.cachedIn(viewModelScope)

    val newsFlow get() = _newsFlow
}