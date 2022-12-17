package com.lanier.rocoguide.ui.common.pullrefresh

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.lanier.rocoguide.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Create by Eric
 * on 2022/12/17
 */
/**
 * An example to show how [pullRefresh] could be used to build a custom
 * pull to refresh component.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomPullRefreshSample() {
    val refreshScope = rememberCoroutineScope()
    val threshold = with(LocalDensity.current) { 160.dp.toPx() }

    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }
    var currentDistance by remember { mutableStateOf(0f) }

    val progress = currentDistance / threshold

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        itemCount += 5
        refreshing = false
    }

    fun onPull(pullDelta: Float): Float = when {
        refreshing -> 0f
        else -> {
            val newOffset = (currentDistance + pullDelta).coerceAtLeast(0f)
            val dragConsumed = newOffset - currentDistance
            currentDistance = newOffset
            dragConsumed
        }
    }

    suspend fun onRelease() {
        if (refreshing) return // Already refreshing - don't call refresh again.
        if (currentDistance > threshold) refresh()

        animate(initialValue = currentDistance, targetValue = 0f) { value, _ ->
            currentDistance = value
        }
    }

    Box(Modifier.pullRefresh(::onPull, { onRelease() })) {
        LazyColumn {
            if (!refreshing) {
                items(itemCount) {
                    Text(text = "Item ${itemCount - it}", Modifier.fillMaxWidth())
                }
            }
        }

        // Custom progress indicator
        AnimatedVisibility(visible = (refreshing || progress > 0)) {
            if (refreshing) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                LinearProgressIndicator(progress, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PullRefreshIndicatorGulu(
    refreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    CustomPullRefreshImgIndicator(R.drawable.ic_gulu_base_bg, refreshing, state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRefreshIndicatorJelly(
    refreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    CustomPullRefreshGifIndicator(R.drawable.ic_jelly, R.drawable.ic_jelly_gif, refreshing, state, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomPullRefreshGifIndicator(
    @DrawableRes defDrawable: Int,
    @DrawableRes gifDrawable: Int,
    refreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .size(40.dp)
            .pullRefreshIndicatorTransform(state),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = if (state.progress > 0 || refreshing) 20.dp else 0.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (refreshing) {
                val context = LocalContext.current
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (context.applicationInfo.targetSdkVersion >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                AsyncImage(
                    model = gifDrawable,
                    contentDescription = "",
                    imageLoader = imageLoader,
                )
            } else {
                Image(
                    painter = painterResource(id = defDrawable),
                    contentDescription = "",
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomPullRefreshImgIndicator(
    @DrawableRes defDrawable: Int,
    refreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .size(40.dp)
            .pullRefreshIndicatorTransform(state),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = if (state.progress > 0 || refreshing) 20.dp else 0.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (refreshing) {
                LocalRotateImage(painter = painterResource(id = defDrawable))
            } else {
                Image(
                    painter = painterResource(id = defDrawable),
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
internal fun LocalRotateImage(painter: Painter) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
        )
    )
    Image(
        painter = painter,
        contentDescription = "",
        modifier = Modifier
            .graphicsLayer { rotationZ = angle }
    )
}
