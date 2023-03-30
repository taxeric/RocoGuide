package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
    var scale by remember {
        mutableStateOf(1f)
    }
    var transX by remember {
        mutableStateOf(0f)
    }
    var transY by remember {
        mutableStateOf(0f)
    }
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
                    .graphicsLayer(
                        translationX = transX,
                        translationY = transY,
                        scaleX = scale,
                        scaleY = scale,
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rotation ->
                            scale = when {
                                scale < 1f -> 1f
                                scale > 3f -> 3f
                                else -> scale * zoom
                            }
                            if (scale > 1f) {
                                transX += (pan.x * scale)
                                transY += (pan.y * scale)
                            } else {
                                transX = 0f
                                transY = 0f
                            }
                        }
                    }
            )
        }
    }
}
