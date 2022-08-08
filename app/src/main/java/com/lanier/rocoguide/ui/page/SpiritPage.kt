package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.lanier.rocoguide.entity.Search
import com.lanier.rocoguide.entity.SpiritAttributes
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.ui.common.RefreshLazyVerticalGrid
import com.lanier.rocoguide.ui.common.SearchDialog
import com.lanier.rocoguide.vm.SpiritViewModel

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritScreen(navHostController: NavHostController, title: String){
    var showSearchDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            Column {
                SmallTopAppBar(
                    title = { Text(text = title) },
                    actions = {
                        IconButton(onClick = {
                            showSearchDialog = true
                        }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        SpiritMainList(navHostController, innerPadding)
    }
    if (showSearchDialog) {
        SearchDialog(type = Search.Spirit) {
            showSearchDialog = false
            if (it.isNotEmpty()) {
                navHostController.navigate("${ROUTE_SCREEN_SEARCH_LIST}/$it")
            }
        }
    }
}

@Composable
fun SpiritMainList(navHostController: NavHostController, paddingValues: PaddingValues, showSearch: Boolean = false){
    val vm = viewModel<SpiritViewModel>()
    val list = vm.spiritMainList.collectAsLazyPagingItems()
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
            navHostController.navigate("${ROUTE_SCREEN_SPIRIT_DETAIL}/${item.number}")
        }
    ){
        Box {
            AsyncImage(model = item.avatar, contentDescription = "avatar", modifier = Modifier.height(150.dp))
            Text(text = item.number.toString(), fontSize = 16.sp, color = Color.Black, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp))
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            AttrImage(attr = item.primaryAttributes, modifier = Modifier
                .width(30.dp)
                .height(30.dp))
            item.secondaryAttributes.id?.let {
                Spacer(modifier = Modifier.width(10.dp))
                AttrImage(attr = item.secondaryAttributes, modifier = Modifier
                    .width(30.dp)
                    .height(30.dp))
            }
        }
        Text(text = item.name, textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth())
    }
}

@Composable
fun AttrImage(modifier: Modifier = Modifier, attr: SpiritAttributes) {
    Image(painter = painterResource(id = when (attr.id) {
        1 -> R.drawable.ic_attr_1
        2 -> R.drawable.ic_attr_2
        3 -> R.drawable.ic_attr_3
        4 -> R.drawable.ic_attr_4
        5 -> R.drawable.ic_attr_5
        6 -> R.drawable.ic_attr_6
        7 -> R.drawable.ic_attr_7
        8 -> R.drawable.ic_attr_8
        9 -> R.drawable.ic_attr_9
        10 -> R.drawable.ic_attr_10
        11 -> R.drawable.ic_attr_11
        12 -> R.drawable.ic_attr_12
        13 -> R.drawable.ic_attr_13
        14 -> R.drawable.ic_attr_14
        15 -> R.drawable.ic_attr_15
        16 -> R.drawable.ic_attr_16
        17 -> R.drawable.ic_attr_17
        18 -> R.drawable.ic_attr_18
        19 -> R.drawable.ic_attr_19
        20 -> R.drawable.ic_attr_20
        21 -> R.drawable.ic_attr_21
        else -> R.drawable.ic_attr_0
    }), contentDescription = "attr", modifier = modifier)
}
