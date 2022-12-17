package com.lanier.rocoguide.vm.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.LineageEntity

/**
 * Create by Eric
 * on 2022/12/17
 */
class LineageSource: PagingSource<Int, LineageEntity>() {
    override fun getRefreshKey(state: PagingState<Int, LineageEntity>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LineageEntity> {
        val entity = Net.service.getLineage()
        if (entity.code == 200) {
            return LoadResult.Page(entity.data, null, null)
        }
        return LoadResult.Error(Throwable(entity.msg))
    }
}