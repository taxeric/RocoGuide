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
fun SingleTitle(
    title: String,
    paddingValues: PaddingValues = PaddingValues(16.dp, 20.dp),
    dividerHeight: Float = 1f
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(dividerHeight.dp),
            color = MaterialTheme.colorScheme.inversePrimary)
    }
}

@Composable
fun TitleTextWithRipple(
    title: String,
    text: String,
    spacerHeight: Dp = 8.dp,
    titleSize: TextUnit = 19.sp,
    titleWeight: FontWeight? = FontWeight.Normal,
    textColor: Color = Color.Gray,
    onClick: () -> Unit = {}
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(16.dp, 20.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontSize = titleSize), fontWeight = titleWeight)
        Spacer(modifier = Modifier.height(spacerHeight))
        Text(text = text, color = textColor, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp), lineHeight = 14.sp)
    }
}
