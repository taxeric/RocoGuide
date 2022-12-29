package com.lanier.rocoguide.vm.abnormal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.abnormal.AbnormalSource

/**
 * Create by Eric
 * on 2022/12/17
 */
class AbnormalVM: ViewModel() {

    val pager = Pager(
        PagingConfig(10, enablePlaceholders = false)
    ) {
        AbnormalSource()
    }.flow.cachedIn(viewModelScope)
}