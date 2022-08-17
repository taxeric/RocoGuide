package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.ui.common.SearchBaseScaffold
import com.lanier.rocoguide.vm.SearchViewModel
import com.lanier.rocoguide.vm.factory.SearchVMFactory

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 15:26
 * Desc  :
 */
@Composable
fun SearchSpiritScreen(navController: NavHostController, keywords: String) {
    val vm = viewModel<SearchViewModel>(factory = SearchVMFactory(keywords))
    val list = vm.searchResult.collectAsLazyPagingItems()
    SearchBaseScaffold(type = Search.Spirit, onBack = {
        navController.popBackStack()
    }) {
        Column(modifier = Modifier.padding(it)) {
            SpiritMainListImpl(list = list, navHostController = navController)
        }
    }
}
