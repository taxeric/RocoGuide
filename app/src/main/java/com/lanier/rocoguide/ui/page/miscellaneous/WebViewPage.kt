package com.lanier.rocoguide.ui.page.miscellaneous

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold

/**
 * Create by Eric
 * on 2022/7/25
 */
@Composable
fun WebViewByUrl(navController: NavController, title: String, url: String){
    EnableBackBaseScaffold(title = title, onBack = { navController.popBackStack() }) {
        WebViewImpl(it, url)
    }
}

@Composable
fun WebViewImpl(paddingValues: PaddingValues, url: String){
    val state = rememberWebViewState(url)
    Box(modifier = Modifier.padding(paddingValues)) {
        WebView(
            state = state,
            captureBackPresses = false,
            onCreated = {
                it.settings.run {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
            }
        )
    }
}
