package com.lanier.rocoguide.ui.glance

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/26 16:04
 * Desc  :
 */
class NewsGlanceWidget: GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(modifier = GlanceModifier.fillMaxSize().background(Color.White)) {
            Text(text = "hello 天威狮鹫")
        }
    }
}