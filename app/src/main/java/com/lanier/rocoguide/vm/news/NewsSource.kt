package com.lanier.rocoguide.vm.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.entity.NewsData
import com.lanier.rocoguide.entity.NewsList

/**
 * Create by Eric
 * on 2022/7/29
 */
class NewsSource(
    private val repo: NewsRepo = NewsRepo()
): PagingSource<Int, NewsData>() {
    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
        val nextPage = params.key ?: 1
        val response = repo.getNewsList(nextPage).getOrDefault(NewsList())
        if (response.code == 200) {
            return LoadResult.Page(response.data, null, if (response.data.isEmpty()) null else nextPage + 1)
        }
        return LoadResult.Error(Throwable(response.msg))
    }
}