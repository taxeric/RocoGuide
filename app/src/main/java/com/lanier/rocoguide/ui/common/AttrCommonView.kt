package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.lanier.rocoguide.R
import com.lanier.rocoguide.entity.SpiritAttributes

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 16:20
 * Desc  :
 */
@Composable
fun AttrImage(modifier: Modifier = Modifier, attr: SpiritAttributes) {
    Image(painter = painterResource(id = when (attr.id) {
        1 -> R.drawable.ic_attr_1
        2 -> R.drawable.ic_attr_2
        3 -> R.drawable.ic_attr_3
        4 -> R.drawable.ic_attr_4
        5 -> R.drawable.ic_attr_5
        6 -> R.drawable.ic_attr_6
        7 -> R.drawable.ic_attr_7
        8 -> R.drawable.ic_attr_8
        9 -> R.drawable.ic_attr_9
        10 -> R.drawable.ic_attr_10
        11 -> R.drawable.ic_attr_11
        12 -> R.drawable.ic_attr_12
        13 -> R.drawable.ic_attr_13
        14 -> R.drawable.ic_attr_14
        15 -> R.drawable.ic_attr_15
        16 -> R.drawable.ic_attr_16
        17 -> R.drawable.ic_attr_17
        18 -> R.drawable.ic_attr_18
        19 -> R.drawable.ic_attr_19
        20 -> R.drawable.ic_attr_20
        21 -> R.drawable.ic_attr_21
        else -> R.drawable.ic_attr_0
    }), contentDescription = "attr", contentScale = ContentScale.Crop, modifier = modifier)
}
