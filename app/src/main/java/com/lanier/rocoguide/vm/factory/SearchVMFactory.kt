package com.lanier.rocoguide.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lanier.rocoguide.vm.SearchViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/9 14:05
 * Desc  :
 */
class SearchVMFactory(private val keywords: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(keywords) as T
    }
}