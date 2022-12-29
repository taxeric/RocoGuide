package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.lanier.rocoguide.R
import com.lanier.rocoguide.base.ROUTE_SCREEN_WEB_VIEW
import com.lanier.rocoguide.entity.NewsData
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.HomepageLuLuDialog
import com.lanier.rocoguide.ui.common.PullRefreshIndicatorType
import com.lanier.rocoguide.ui.common.RefreshLazyColumn
import com.lanier.rocoguide.ui.theme.ExtendedTheme
import com.lanier.rocoguide.vm.news.NewsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun NewsScreen(navController: NavController, title: String){
    var luluDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val sdkVersion = context.applicationInfo.targetSdkVersion
    CommonBaseScaffold(
        title = title,
        actions = {
            IconButton(onClick = {
                luluDialog = true
            }) {
                Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                    val imageLoader = ImageLoader.Builder(context)
                        .components {
                            if (sdkVersion >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }
                        .build()
                    AsyncImage(
                        model = R.drawable.ic_lulu_bg,
                        contentDescription = "",
                        imageLoader = imageLoader,
                        modifier = Modifier.fillMaxSize()
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_lulu_max),
                        contentScale = ContentScale.Crop,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }) {
        NewsMain(navController = navController, padding = it)
    }
    if (luluDialog) {
        HomepageLuLuDialog {
            luluDialog = false
        }
    }
}

@Composable
fun NewsMain(navController: NavController, padding: PaddingValues){
    Column(
        modifier = Modifier
            .padding(padding)
            .background(ExtendedTheme.colors.defaultMainBackground)
    ) {
        NewsList(navController = navController)
    }
}

@Composable
fun NewsList(navController: NavController){
    val vm: NewsViewModel = viewModel()
    val list = vm.newsFlow.collectAsLazyPagingItems()
    RefreshLazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp),
        pullRefreshStateType = PullRefreshIndicatorType.Gulu,
        data = list
    ){ _, data ->
        NewsItem(navController = navController, item = data)
    }
}

@Composable
fun NewsItem(navController: NavController, item: NewsData){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ExtendedTheme.colors.defaultLazyItemBackground)
    ) {
        Text(
            text = item.updateTime,
            color = MaterialTheme.colorScheme.outline,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 5.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
                .clickable {
                    val encodeUrl = URLEncoder.encode(item.url, StandardCharsets.UTF_8.toString())
                    navController.navigate("${ROUTE_SCREEN_WEB_VIEW}/正文/$encodeUrl")
                }
                .padding(10.dp)
        )
    }
}
