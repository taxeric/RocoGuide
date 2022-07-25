package com.lanier.rocoguide.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

/**
 * Create by Eric
 * on 2022/7/25
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewByUrl(navController: NavController, title: String, url: String){
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            SmallTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
    ) { innerPadding ->
        WebViewImpl(innerPadding, url)
    }
}

@Composable
fun WebViewImpl(paddingValues: PaddingValues, url: String){
    val state = rememberWebViewState(url)
    Box(modifier = Modifier.padding(paddingValues)) {
        WebView(state = state, captureBackPresses = false)
    }
}
