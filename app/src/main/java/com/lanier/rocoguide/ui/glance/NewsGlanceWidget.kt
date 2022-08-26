package com.lanier.rocoguide.ui.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import com.lanier.rocoguide.entity.NewsData

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
            val news = currentState<NewsData>()
            Text(text = news.title)
        }
    }

    override val stateDefinition = NewsGlanceDefinition
}

/**
 * Force update the data after user click
 */
class ForceUpdateNews: ActionCallback{
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        NewsWorker.enqueue(context, true)
    }
}
