package com.lanier.rocoguide.ui.page.miscellaneous

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.ui.common.CommonBaseScaffold
import com.lanier.rocoguide.ui.common.EnableBackBaseScaffold
import com.lanier.rocoguide.ui.common.TitleBarSize
import com.lanier.rocoguide.ui.common.TitleTextWithRipple

/**
 * Create by Eric
 * on 2022/9/10
 */
@Composable
fun ThankScreen(navHostController: NavHostController, title: String){
    CommonBaseScaffold(title = title, showNavigationIcon = true,
        onBack = {
            navHostController.popBackStack()
        }, titleBarSize = TitleBarSize.Large) {
        ThankScreenImpl(navHostController, paddingValues = it)
    }
}

@Composable
fun ThankScreenImpl(navHostController: NavHostController, paddingValues: PaddingValues){
    val context = LocalContext.current
    Column(modifier = Modifier.padding(paddingValues)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(LocalCache.thanksList) { index, item ->
                TitleTextWithRipple(title = item.name, text = item.desc) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                    context.startActivity(intent)
                }
            }
        }
    }
}
