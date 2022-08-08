package com.lanier.rocoguide.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.vm.repo.SpiritRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 14:38
 * Desc  :
 */
class SearchViewModel : ViewModel(){

    private val repo = SpiritRepo()

    val searchResult get() = fun (keywords: String) = repo.searchSpirit(keywords).cachedIn(viewModelScope)
}
