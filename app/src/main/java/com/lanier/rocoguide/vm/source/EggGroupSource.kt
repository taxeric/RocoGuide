package com.lanier.rocoguide.vm.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.entity.EggGroupList
import com.lanier.rocoguide.entity.SpiritEggGroup
import com.lanier.rocoguide.vm.repo.EggGroupRepo

/**
 * Create by Eric
 * on 2022/7/29
 */
class EggGroupSource(
    private val repo: EggGroupRepo = EggGroupRepo()
): PagingSource<Int, SpiritEggGroup>() {
    override fun getRefreshKey(state: PagingState<Int, SpiritEggGroup>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpiritEggGroup> {
        val nextPage = params.key ?: 1
        val response = repo.getEggGroup().getOrDefault(EggGroupList())
        if (response.code == 200) {
            return LoadResult.Page(response.data, null, if (response.data.size >= response.total) null else nextPage + 1)
        }
        return LoadResult.Error(Throwable(response.msg))
    }
}