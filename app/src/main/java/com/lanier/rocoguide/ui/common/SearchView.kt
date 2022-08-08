package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.layout.PaddingValues
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBaseScaffold(type: Search, onBack: () -> Unit, content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = "搜索${type.title}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
