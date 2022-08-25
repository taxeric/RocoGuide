package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/24 17:20
 * Desc  :
 */
@Composable
fun SingleTitle(title: String, dividerHeight: Float = 1f){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 0.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(dividerHeight.dp),
            color = MaterialTheme.colorScheme.inversePrimary)
    }
}

@Composable
fun TitleText(
    title: String,
    text: String,
    spacerHeight: Dp = 8.dp,
    titleSize: TextUnit = 18.sp,
    titleWeight: FontWeight? = FontWeight.Bold,
    textColor: Color = Color.Gray,
    longPress: () -> Unit = {}
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .longPress { longPress() }
        .padding(10.dp, 4.dp)
    ) {
        Text(text = title, fontSize = titleSize, fontWeight = titleWeight)
        Spacer(modifier = Modifier.height(spacerHeight))
        Text(text = text, color = textColor, fontSize = 12.sp, lineHeight = 14.sp)
    }
}
