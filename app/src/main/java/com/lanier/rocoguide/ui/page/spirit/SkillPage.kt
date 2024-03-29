package com.lanier.rocoguide.ui.page.spirit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_SEARCH_LIST
import com.lanier.rocoguide.base.ROUTE_SCREEN_SKILL_DETAIL
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.vm.skill.SkillViewModel

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 9:50
 * Desc  :
 */
@Composable
fun SkillScreen(navHostController: NavHostController, title: String){
    var showSearchDialog by remember {
        mutableStateOf(false)
    }
    EnableBackBaseScaffoldWithActions(title = title, onBack = {
        navHostController.popBackStack()
    }, actions = {
        IconButton(onClick = {
            showSearchDialog = true
        }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
        }
    }) {
        SkillMainList(navHostController, it)
    }
    if (showSearchDialog) {
        SearchDialog(type = Search.Skill, label = "技能名") {
            showSearchDialog = false
            if (it.isNotEmpty()) {
                navHostController.navigate("$ROUTE_SCREEN_SEARCH_LIST/$it/${Search.Skill.type}")
            }
        }
    }
}

@Composable
fun SkillMainList(navHostController: NavHostController, paddingValues: PaddingValues){
    val vm = viewModel<SkillViewModel>()
    val list = vm.skillMainListFlow.collectAsLazyPagingItems()
    Column(modifier = Modifier.padding(paddingValues)) {
        SkillMainListImpl(list, navHostController)
    }
}

@Composable
fun SkillMainListImpl(list: LazyPagingItems<Skill>, navHostController: NavHostController){
    RefreshLazyColumn(data = list, verticalArrangement = Arrangement.spacedBy(10.dp)) { index, data ->
        SkillItem(navHostController, item = data)
    }
}

@Composable
fun SkillItem(navHostController: NavHostController, item: Skill){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                navHostController.navigate("$ROUTE_SCREEN_SKILL_DETAIL/${Gson().toJson(item)}")
            },
    ) {
        AttrImage(
            attr = item.attributes,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(28.dp)
                .padding(10.dp, 0.dp)
                .weight(2f)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
        ) {
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .align(Alignment.CenterStart)
            )
            if (item.skillType.id < 3) {
                Image(
                    painter = painterResource(
                        id = if (item.skillType.id == 1)
                            R.drawable.ic_attack_phycical
                        else
                            R.drawable.ic_attack_magic
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(10.dp, 0.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}
