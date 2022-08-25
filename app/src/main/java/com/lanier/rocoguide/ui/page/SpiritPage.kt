package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.lanier.rocoguide.base.ROUTE_SCREEN_SEARCH_LIST
import com.lanier.rocoguide.base.ROUTE_SCREEN_SPIRIT_DETAIL
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.vm.SpiritViewModel

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun SpiritScreen(navHostController: NavHostController, title: String){
    var showSearchDialog by remember {
        mutableStateOf(false)
    }
    ActionsBaseScaffold(title = title, actions = {
        IconButton(onClick = {
            showSearchDialog = true
        }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
        }
    }) {
        SpiritMainList(navHostController, it)
    }
    if (showSearchDialog) {
        SearchDialog(type = Search.Spirit, label = "精灵名") {
            showSearchDialog = false
            if (it.isNotEmpty()) {
                navHostController.navigate("${ROUTE_SCREEN_SEARCH_LIST}/$it/${Search.Spirit.type}")
            }
        }
    }
}

@Composable
fun SpiritMainList(navHostController: NavHostController, paddingValues: PaddingValues){
    val vm = viewModel<SpiritViewModel>()
    val list = vm.spiritMainListFlow.collectAsLazyPagingItems()
    Column(modifier = Modifier.padding(paddingValues)) {
        SpiritMainListImpl(list, navHostController)
    }
}

@Composable
fun SpiritMainListImpl(list: LazyPagingItems<SpiritEntity>, navHostController: NavHostController){
    RefreshLazyVerticalGrid(data = list, contentPadding = PaddingValues(10.dp, 0.dp)) { index, data ->
        SpiritItem(navHostController, item = data)
    }
}

@Composable
fun SpiritItem(navHostController: NavHostController, item: SpiritEntity){
    Column(modifier = Modifier
        .height(210.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable {
//            "search -> ${item.number}".logI()
            navHostController.navigate("${ROUTE_SCREEN_SPIRIT_DETAIL}/${item.id}")
        }
    ){
        AsyncImage(model = item.avatar, contentDescription = "avatar", modifier = Modifier.height(150.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            AttrImage(attr = item.primaryAttributes, modifier = Modifier
                .width(20.dp)
                .height(20.dp))
            item.secondaryAttributes.id?.let {
                Spacer(modifier = Modifier.width(10.dp))
                AttrImage(attr = item.secondaryAttributes, modifier = Modifier
                    .width(20.dp)
                    .height(20.dp))
            }
        }
        Text(text = item.number, textAlign = TextAlign.Center, fontSize = 13.sp, modifier = Modifier
            .fillMaxWidth())
        Text(text = item.name, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth())
    }
}
