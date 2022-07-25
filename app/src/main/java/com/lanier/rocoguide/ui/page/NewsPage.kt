package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lanier.rocoguide.base.ROUTE_SCREEN_WEB_VIEW
import com.lanier.rocoguide.entity.NewsData
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
    val list = remember {
        mutableStateListOf<NewsData>().apply {
            add(NewsData(title = "洛克王国7月22日更新速递—小凡！！！"))
            add(NewsData(title = "洛克王国7月22日部分版本情报公开"))
            add(NewsData(title = "洛克王国7月15日更新公告—十二周年庆！！！"))
            add(NewsData(title = "【活动抢先知】7.15周年庆(下)部分活动攻略"))
            add(NewsData(title = "洛克王国7月8日更新公告——晴璋之鹿！！！"))
            add(NewsData(title = "【活动抢先知】7.8日周年庆（上）部分活动攻略"))
            add(NewsData(title = "洛克王国7月1日更新公告——琼鹿！！！"))
            add(NewsData(title = "洛克王国6月24日更新公告——小琮！！！"))
            add(NewsData(title = "洛克王国6月17日更新公告——谕灵王座！！！"))
            add(NewsData(title = "2022洛克王国 周年庆官宣（下）"))
        }
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()){
        items(list){
            NewsItem(navController = navController, item = it)
        }
    }
}

@Composable
fun NewsItem(navController: NavController, item: NewsData){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clickable {
            val encodeUrl = URLEncoder.encode(item.url, StandardCharsets.UTF_8.toString())
            navController.navigate("${ROUTE_SCREEN_WEB_VIEW}/正文/$encodeUrl")
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 16.sp,
        )
    }
}
