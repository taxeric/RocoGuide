package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_WEB_VIEW
import com.lanier.rocoguide.entity.NewsData
import com.lanier.rocoguide.ui.common.RefreshLazyColumn
import com.lanier.rocoguide.vm.NewsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController, title: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
                actions = {
                    IconButton(onClick = {  }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_dimo_1),
                            contentDescription = "",
                            modifier = Modifier.clip(RoundedCornerShape(50.dp))
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        NewsMain(navController = navController, padding = innerPadding)
    }
}

@Composable
fun NewsMain(navController: NavController, padding: PaddingValues){
    Column(modifier = Modifier.padding(padding)) {
        NewsList(navController = navController)
    }
}

@Composable
fun NewsList(navController: NavController){
    val vm: NewsViewModel = viewModel()
    val list = vm.newsFlow.collectAsLazyPagingItems()
    RefreshLazyColumn(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        data = list){index, data ->
        NewsItem(navController = navController, item = data)
    }
}

@Composable
fun NewsItem(navController: NavController, item: NewsData){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            val encodeUrl = URLEncoder.encode(item.url, StandardCharsets.UTF_8.toString())
            navController.navigate("${ROUTE_SCREEN_WEB_VIEW}/正文/$encodeUrl")
        },
    ) {
        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(10.dp, 0.dp)
        )
//        Icon(imageVector = Icons.Filled.AddCircle)
    }
}
