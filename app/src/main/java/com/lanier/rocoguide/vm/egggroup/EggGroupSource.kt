package com.lanier.rocoguide.vm.egggroup

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.entity.EggGroupList
import com.lanier.rocoguide.entity.SpiritEggGroup

/**
 * Create by Eric
 * on 2022/7/29
 */
class EggGroupSource(
    private val repo: EggGroupRepo = EggGroupRepo()
): PagingSource<Int, SpiritEggGroup>() {
    override fun getRefreshKey(state: PagingState<Int, SpiritEggGroup>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpiritEggGroup> {
        if (LocalCache.localCacheEggGroupInfo.isNotEmpty()) {
            LocalCache.localCacheEggGroupInfo.forEach {
                it.randomBackgroundColor = LocalCache.generateRandomColor()
            }
            return LoadResult.Page(LocalCache.localCacheEggGroupInfo, null, null)
        }
        val nextPage = params.key ?: 1
        val response = repo.getEggGroup().getOrDefault(EggGroupList())
        response.data.forEach {
            it.randomBackgroundColor = LocalCache.generateRandomColor()
        }
        if (response.code == 200) {
            LocalCache.localCacheEggGroupInfo.clear()
            LocalCache.localCacheEggGroupInfo.addAll(response.data)
            return LoadResult.Page(response.data, null, if (response.data.size >= response.total) null else nextPage + 1)
        }
        return LoadResult.Error(Throwable(response.msg))
    }
}