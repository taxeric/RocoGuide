package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.lanier.rocoguide.ui.theme.ExtendedTheme

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 14:30
 * Desc  :
 */
fun Modifier.longPress(longPressEvent: () -> Unit): Modifier {
    return pointerInput(this){
        detectTapGestures(onLongPress = { longPressEvent() })
    }
}

/**
 * 点击事件,不带波纹效果
 */
fun Modifier.press(pressEvent: () -> Unit): Modifier {
    return pointerInput(this){
        detectTapGestures(onPress = { pressEvent() })
    }
}

@Composable
fun Modifier.baseBackground(): Modifier {
    return background(ExtendedTheme.colors.defaultMainBackground)
}
