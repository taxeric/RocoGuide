package com.lanier.rocoguide.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.lanier.rocoguide.base.ROUTE_SCREEN_SPIRIT_DETAIL
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.RefreshLazyVerticalGrid
import com.lanier.rocoguide.vm.SpiritViewModel

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, title: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            var startSearch by remember {
                mutableStateOf(false)
            }
            var searchStr by remember {
                mutableStateOf("")
            }
            Column {
                SmallTopAppBar(
                    title = { Text(text = title) },
                    actions = {
                        IconButton(onClick = {
                            startSearch = !startSearch
                        }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        }
                    }
                )
                AnimatedVisibility(visible = startSearch) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = searchStr,
                            onValueChange = {
                                searchStr = it
                            },
                            label = {
                                Text(text = "搜索")
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    searchStr = ""
                                }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        SpiritMainList(navHostController, innerPadding)
    }
}

@Composable
fun SpiritMainList(navHostController: NavHostController, paddingValues: PaddingValues){
    val vm = viewModel<SpiritViewModel>()
    val list = vm.spiritMainList.collectAsLazyPagingItems()
    Column(modifier = Modifier.padding(paddingValues)) {
        SpiritMainListImpl(list, navHostController)
    }
}

@Composable
fun SpiritMainListImpl(list: LazyPagingItems<SpiritEntity>, navHostController: NavHostController){
    RefreshLazyVerticalGrid(data = list) { index, data ->
        SpiritItem(navHostController, item = data)
    }
}

@Composable
fun SpiritItem(navHostController: NavHostController, item: SpiritEntity){
    Column(modifier = Modifier
        .height(200.dp)
        .background(Color.Green)
        .clip(RoundedCornerShape(10.dp))
        .clickable {
            navHostController.navigate("${ROUTE_SCREEN_SPIRIT_DETAIL}/${item.number}")
        }
    ){
        Box {
            AsyncImage(model = item.avatar, contentDescription = "avatar", modifier = Modifier.height(170.dp))
            Text(text = item.number.toString(), fontSize = 16.sp, color = Color.Black, modifier = Modifier.align(Alignment.TopEnd))
        }
        Text(text = item.name, textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier.fillMaxHeight())
    }
}
