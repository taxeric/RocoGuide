package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lanier.rocoguide.entity.Search

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 15:18
 * Desc  :
 */
sealed class TitleBarSize{
    object Small: TitleBarSize()
    object Medium: TitleBarSize()
    object Large: TitleBarSize()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBaseScaffold(
    titleBarSize: TitleBarSize = TitleBarSize.Small,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    showNavigationIcon: Boolean = false,
    snackbarHost: @Composable () -> Unit = {},
    onBack: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            when (titleBarSize) {
                is TitleBarSize.Large -> LargeTopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        if (showNavigationIcon) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "",
                                )
                            }
                        }
                    },
                    actions = actions
                )
                is TitleBarSize.Medium -> MediumTopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        if (showNavigationIcon) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "",
                                )
                            }
                        }
                    },
                    actions = actions
                )
                else -> TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        if (showNavigationIcon) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "",
                                )
                            }
                        }
                    },
                    actions = actions
                )
            }
        },
        snackbarHost = snackbarHost
    ) { innerPadding ->
        content(innerPadding)
    }
}

/**
 * 只显示标题
 */
@Composable
fun EmptyBaseScaffold(title: String, content: @Composable (PaddingValues) -> Unit){
    CommonBaseScaffold(title = title) {
        content(it)
    }
}

/**
 * 显示标题和action
 */
@Composable
fun ActionsBaseScaffold(title: String, actions: @Composable RowScope.() -> Unit, content: @Composable (PaddingValues) -> Unit){
    CommonBaseScaffold(title = title, actions = actions) {
        content(it)
    }
}

/**
 * 显示标题和返回
 */
@Composable
fun EnableBackBaseScaffold(title: String, onBack: () -> Unit, content: @Composable (PaddingValues) -> Unit){
    CommonBaseScaffold(title = title, showNavigationIcon = true, onBack = onBack) {
        content(it)
    }
}

/**
 * 显示标题和返回和action
 */
@Composable
fun EnableBackBaseScaffoldWithActions(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    CommonBaseScaffold(title = title, showNavigationIcon = true, onBack = onBack, actions = actions) {
        content(it)
    }
}

@Composable
fun EnableBackBaseScaffoldWithActions(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    snackbar: @Composable () -> Unit
){
    CommonBaseScaffold(title = title, showNavigationIcon = true, onBack = onBack, actions = actions, snackbarHost = snackbar) {
        content(it)
    }
}

/**
 * 搜索
 */
@Composable
fun SearchBaseScaffold(type: Search, onBack: () -> Unit, content: @Composable (PaddingValues) -> Unit){
    CommonBaseScaffold(title = "搜索${type.title}", showNavigationIcon = true, onBack = onBack) {
        content(it)
    }
}
