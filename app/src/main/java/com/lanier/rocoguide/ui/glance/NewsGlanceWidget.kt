package com.lanier.rocoguide.ui.glance

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.background
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.lanier.rocoguide.MainActivity
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
        Column(modifier = GlanceModifier.fillMaxSize().background(day = GlanceTheme.lightColors.background,
            night = GlanceTheme.darkColor.background)) {
            val news = currentState<NewsData>()
            Text(text = news.title,
                modifier = GlanceModifier
                .padding(10.dp)
                .clickable(startActivityAction(MainActivity::class.java))
            )
        }
    }

    override val stateDefinition = NewsGlanceDefinition

    private fun <T: ComponentActivity> startActivityAction(clazz: Class<T>): Action{
        return StartActivityClassAction(clazz, actionParametersOf())
    }
}

/**
 * Force update the data after user click
 */
class ForceUpdateNews: ActionCallback{
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        NewsWorker.enqueue(context, true)
    }
}
