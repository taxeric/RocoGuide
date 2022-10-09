package com.lanier.rocoguide.ui.glance

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.google.gson.Gson
import com.lanier.lib_net.PrintInterceptor
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.NewsData
import com.lanier.rocoguide.entity.NewsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.Duration

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/26 16:56
 * Desc  : api 26 or higher
 */
class NewsWorker(
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val remoteViewClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(PrintInterceptor())
        .build()

    companion object{

        private val uniqueWorkName = NewsWorker::class.java.name

        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = PeriodicWorkRequestBuilder<NewsWorker>(
                Duration.ofMinutes(30)
            )
            var workPolicy = ExistingPeriodicWorkPolicy.KEEP

            if (force) {
                workPolicy = ExistingPeriodicWorkPolicy.REPLACE
            }

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(NewsGlanceWidget::class.java)
        return try {
            setWidgetState(glanceIds,
                getNewsData("${LocalCache.BASE_URL}api/news/v1/page?page=1&amount=10")
            )
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun getNewsData(baseUrl: String): NewsData {
        val request = Request.Builder()
            .url(baseUrl)
            .get()
            .build()
        val call = remoteViewClient.newCall(request)
        return withContext(Dispatchers.Default) {
            val response = call.execute()
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                withContext(Dispatchers.Main) {
                    if (body.isEmpty()) {
                        NewsData(title = "获取数据失败")
                    } else {
                        val listData = Gson().fromJson(body, NewsList::class.java)
                        if (listData.data.isEmpty()) {
                            NewsData(title = "没有新闻")
                        } else {
                            listData.data[0]
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    NewsData(title = "获取数据失败")
                }
            }
        }
    }

    private suspend fun setWidgetState(glanceIds: List<GlanceId>, newState: NewsData) {
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = NewsGlanceDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }
        NewsGlanceWidget().updateAll(context)
    }
}