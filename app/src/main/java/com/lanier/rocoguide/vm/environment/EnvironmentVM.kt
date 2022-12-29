package com.lanier.rocoguide.vm.environment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lanier.rocoguide.vm.environment.EnvironmentSource

/**
 * Create by Eric
 * on 2022/12/17
 */
class EnvironmentVM: ViewModel() {

    val pager = Pager(
        PagingConfig(10, enablePlaceholders = false)
    ) {
        EnvironmentSource()
    }.flow.cachedIn(viewModelScope)
}