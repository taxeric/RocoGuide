package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * Create by Eric
 * on 2022/7/29
 */
@Composable
fun <T: Any> RefreshLazyColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    data: LazyPagingItems<T>,
    emptyView: @Composable () -> Unit = {
        DefaultListEmptyView()
    },
    loadingView: @Composable () -> Unit = {
        DefaultListLoadingView()
    },
    errorView: @Composable (msg: String) -> Unit = {
        DefaultListErrorView(msg = it) {
            data.retry()
        }
    },
    key: ((Int, T) -> Any)? = null,
    itemView: @Composable (Int, T) -> Unit,
){
    SwipeRefresh(state = rememberSwipeRefreshState(data.loadState.refresh is LoadState.Loading), onRefresh = { data.refresh() }) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = verticalArrangement,
            contentPadding = contentPadding
        ){
            itemsIndexed(data, key = key){index, data ->
                itemView(index, data!!)
            }
            when {
                data.loadState.append is LoadState.Loading -> {
                    item {
                        loadingView()
                    }
                }
                data.loadState.append is LoadState.Error -> {
                    item {
                        val msg = (data.loadState.append as LoadState.Error).error.message
                        errorView("出错了")
                    }
                }
                data.loadState.refresh is LoadState.NotLoading -> {
                    if (data.itemCount <= 0) {
                        item {
                            emptyView()
                        }
                    }
                }
                data.loadState.refresh is LoadState.Error -> {
                    item {
                        val msg = (data.loadState.refresh as LoadState.Error).error.message
                        errorView(msg ?: "出错了")
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultListEmptyView(msg: String = "没有更多数据"){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.White), contentAlignment = Alignment.Center) {
        Text(text = msg, modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}

@Composable
fun DefaultListLoadingView(){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultListErrorView(msg: String, retry: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { retry()}){
            Text(text = "$msg 点击重试")
        }
    }
}
