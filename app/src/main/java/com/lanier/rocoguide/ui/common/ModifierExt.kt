package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

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
