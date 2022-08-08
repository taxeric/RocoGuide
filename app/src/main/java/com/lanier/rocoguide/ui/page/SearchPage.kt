package com.lanier.rocoguide.ui.page

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.ui.common.SearchBaseScaffold
import com.lanier.rocoguide.vm.SearchViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 15:26
 * Desc  :
 */
@Composable
fun SearchSpiritScreen(navController: NavHostController, keywords: String) {
    val vm = viewModel<SearchViewModel>()
    val list = vm.searchResult(keywords).collectAsLazyPagingItems()
    SearchBaseScaffold(type = Search.Spirit, onBack = {
        navController.popBackStack()
    }) {
        SpiritMainListImpl(list = list, navHostController = navController)
    }
}
