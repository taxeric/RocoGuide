package com.lanier.rocoguide.vm.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.entity.NewsData
import com.lanier.rocoguide.entity.NewsList
import com.lanier.rocoguide.entity.WrapNews

/**
 * Create by Eric
 * on 2022/7/29
 */
class NewsSource(
    private val repo: NewsRepo = NewsRepo()
): PagingSource<Int, WrapNews>() {
    override fun getRefreshKey(state: PagingState<Int, WrapNews>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WrapNews> {
        val nextPage = params.key ?: 1
        val response = repo.getNewsList(nextPage).getOrDefault(NewsList())
        val originData = response.data
        val list = handleOriginData(originData)
        if (response.code == 200) {
            return LoadResult.Page(list, null, if (response.data.isEmpty() || response.data.size < 20) null else nextPage + 1)
        }
        return LoadResult.Error(Throwable(response.msg))
    }

    private fun handleOriginData(originData: List<NewsData>): List<WrapNews> {
        val map = mutableMapOf<String, MutableList<NewsData>>()
        originData.forEach { news ->
            val key = news.updateTime.split(" ")[0]
            if (map.containsKey(key)) {
                map[key]!!.add(news)
            } else {
                map[key] = mutableListOf<NewsData>().apply {
                    add(news)
                }
            }
        }
        val wrapList = mutableListOf<WrapNews>()
        map.forEach { (key, value) ->
            wrapList.add(WrapNews(date = key, data = value))
        }
        return wrapList
    }
}