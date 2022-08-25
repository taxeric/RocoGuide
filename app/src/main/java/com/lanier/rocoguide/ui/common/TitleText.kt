package com.lanier.rocoguide.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
            .height(dividerHeight.dp)
            .background(color = MaterialTheme.colorScheme.inversePrimary))
    }
}

@Composable
fun TitleText(title: String, text: String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp, 0.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = MaterialTheme.colorScheme.inversePrimary))
        Text(text = text)
    }
}
