package com.lanier.rocoguide.vm.environment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.EnvironmentEntity

/**
 * Create by Eric
 * on 2022/12/17
 */
class EnvironmentSource: PagingSource<Int, EnvironmentEntity>() {
    override fun getRefreshKey(state: PagingState<Int, EnvironmentEntity>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EnvironmentEntity> {
        val entity = Net.service.getEnvironment()
        if (entity.code == 200) {
            return LoadResult.Page(entity.data, null, null)
        }
        return LoadResult.Error(Throwable(entity.msg))
    }
}