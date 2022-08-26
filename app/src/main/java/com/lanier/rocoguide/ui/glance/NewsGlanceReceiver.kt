package com.lanier.rocoguide.ui.glance

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/26 16:28
 * Desc  :
 */
class NewsGlanceReceiver(
    override val glanceAppWidget: GlanceAppWidget = NewsGlanceWidget()
) : GlanceAppWidgetReceiver() {

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        NewsWorker.enqueue(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        NewsWorker.cancel(context)
    }
}
