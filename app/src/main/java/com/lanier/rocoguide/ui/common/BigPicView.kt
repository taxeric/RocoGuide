package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

/**
 * Create by Eric
 * on 2022/12/3
 */
@Composable
fun CommonBigPicView(
    navHostController: NavHostController,
    url: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (!url.isNullOrEmpty()) {
            AsyncImage(
                model = url,
                contentDescription = "url",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navHostController.popBackStack()
                    }
            )
        }
    }
}
