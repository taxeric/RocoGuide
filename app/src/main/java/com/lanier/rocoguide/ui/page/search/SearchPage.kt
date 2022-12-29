package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.ui.common.SearchBaseScaffold
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.search.SearchViewModel
import com.lanier.rocoguide.vm.search.SearchVMFactory

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 15:26
 * Desc  :
 */
@Composable
fun SearchBaseScreen(navController: NavHostController, keywords: String, type: Int){
    when (type) {
        Search.Spirit.type -> {
            SearchSpiritScreen(navController, keywords)
        }
        Search.Skill.type -> {
            SearchSkillScreen(navController, keywords)
        }
    }
}

@Composable
fun SearchSpiritScreen(navController: NavHostController, keywords: String) {
    val vm = viewModel<SearchViewModel>(factory = SearchVMFactory(keywords))
    val list = vm.searchSpiritResult.collectAsLazyPagingItems()
    SearchBaseScaffold(type = Search.Spirit, onBack = {
        navController.popBackStack()
    }) {
        Column(modifier = Modifier.padding(it).background(ExtendedTheme.colors.defaultMainBackground)) {
            SpiritMainListImpl(list = list, navHostController = navController)
        }
    }
}

@Composable
fun SearchSkillScreen(navController: NavHostController, keywords: String) {
    val vm = viewModel<SearchViewModel>(factory = SearchVMFactory(keywords))
    val list = vm.searchSkillResult.collectAsLazyPagingItems()
    SearchBaseScaffold(type = Search.Skill, onBack = {
        navController.popBackStack()
    }) {
        Column(modifier = Modifier.padding(it)) {
            SkillMainListImpl(list = list, navHostController = navController)
        }
    }
}
