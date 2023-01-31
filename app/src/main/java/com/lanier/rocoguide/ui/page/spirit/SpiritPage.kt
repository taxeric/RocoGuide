package com.lanier.rocoguide.ui.page.spirit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_SEARCH_LIST
import com.lanier.rocoguide.base.ROUTE_SCREEN_SPIRIT_DETAIL
import com.lanier.rocoguide.entity.FilterSpiritEntity
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.SeriesEntity
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.*
import com.lanier.rocoguide.ui.common.pullrefresh.PullRefreshIndicatorJelly
import com.lanier.rocoguide.ui.common.pullrefresh.pullRefresh
import com.lanier.rocoguide.ui.common.pullrefresh.rememberPullRefreshState
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.spirit.FilterFlow
import com.lanier.rocoguide.vm.spirit.SpiritViewModel
import com.lanier.rocoguide.vm.spirit.getSeries

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun SpiritScreen(navHostController: NavHostController, title: String){
    var showSearchDialog by remember {
        mutableStateOf(false)
    }
    var showFilterDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        getSeries()
    }
    ActionsBaseScaffold(title = title, actions = {
        IconButton(onClick = {
            showSearchDialog = true
        }) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
        }
        IconButton(onClick = {
            showFilterDialog = true
        }) {
            Icon(imageVector = Icons.Outlined.List, contentDescription = "list")
        }
    }) {
        SpiritMainListV2(navHostController, it)
    }
    if (showSearchDialog) {
        SearchDialog(type = Search.Spirit, label = "精灵名") {
            showSearchDialog = false
            if (it.isNotEmpty()) {
                navHostController.navigate("${ROUTE_SCREEN_SEARCH_LIST}/$it/${Search.Spirit.type}")
            }
        }
    }
    if (showFilterDialog) {
        SpiritFilterDialog {
            showFilterDialog = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritMainListV2(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    val vm = viewModel<SpiritViewModel>()
    val refreshing = vm.eventFlow.collectAsState().value
    var seriesId by remember {
        mutableStateOf(1)
    }
    LaunchedEffect(key1 = Unit) {
        FilterFlow.collect {
            seriesId = it.series.id
        }
    }
    if (vm.lastSeries != seriesId) {
        LaunchedEffect(key1 = Unit) {
            vm.getSpirit(seriesId)
        }
    }
    val scrollState = rememberLazyGridState()
    val isReachedBottom by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex + scrollState.layoutInfo
                .visibleItemsInfo.size == scrollState.layoutInfo.totalItemsCount
        }
    }
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { isReachedBottom }
            .collect {
                if (it) {
                    vm.getSpirit(seriesId, refresh = false)
                }
            }
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { vm.getSpirit(seriesId) })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pullRefresh(pullRefreshState)
            .baseBackground()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            itemsIndexed(vm.list) { index, data ->
                SpiritItem(navHostController, item = data)
            }
        }
        PullRefreshIndicatorJelly(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun SpiritMainListImpl(list: LazyPagingItems<SpiritEntity>, navHostController: NavHostController){
    RefreshLazyVerticalGrid(
        data = list,
        contentPadding = PaddingValues(10.dp, 10.dp),
        pullRefreshStateType = PullRefreshIndicatorType.Jelly
    ) { _, data ->
        SpiritItem(navHostController, item = data)
    }
}

@Composable
fun SpiritItem(navHostController: NavHostController, item: SpiritEntity){
    Column(modifier = Modifier
        .height(210.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable {
            navHostController.navigate("${ROUTE_SCREEN_SPIRIT_DETAIL}/${item.id}")
        }
        .background(ExtendedTheme.colors.defaultLazyItemBackground)
    ){
        Box(
            modifier = Modifier.height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_spirit_main_bg),
                contentDescription = ""
            )
            AsyncImage(model = item.avatar, contentDescription = "avatar", )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AttrImage(
                attr = item.primaryAttributes,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
            item.secondaryAttributes.id?.let {
                if (it != -1) {
                    Spacer(modifier = Modifier.width(10.dp))
                    AttrImage(
                        attr = item.secondaryAttributes, modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
            }
        }
        Text(
            text = item.number,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = item.name,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
}
