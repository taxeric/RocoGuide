package com.lanier.rocoguide.vm.abnormal

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.AbnormalEntity

/**
 * Create by Eric
 * on 2022/12/17
 */
class AbnormalSource: PagingSource<Int, AbnormalEntity>(){
    override fun getRefreshKey(state: PagingState<Int, AbnormalEntity>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AbnormalEntity> {
        val entity = Net.service.getAbnormal()
        if (entity.code == 200) {
            return LoadResult.Page(entity.data, null, null)
        }
        return LoadResult.Error(Throwable(entity.msg))
    }
}