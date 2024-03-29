package com.lanier.rocoguide.vm.lineage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

/**
 * Create by Eric
 * on 2022/12/17
 */
class LineageVM: ViewModel() {

    val pager = Pager(
        PagingConfig(10, enablePlaceholders = false)
    ) {
        LineageSource()
    }.flow.cachedIn(viewModelScope)
}