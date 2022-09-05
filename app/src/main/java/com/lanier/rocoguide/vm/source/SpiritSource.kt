package com.lanier.rocoguide.vm.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.entity.SpiritList
import com.lanier.rocoguide.vm.repo.remote.SpiritRemoteData

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:05
 * Desc  :
 */
class SpiritSource(
    private val repo: SpiritRemoteData,
    private val keywords: String = ""
): PagingSource<Int, SpiritEntity>() {

    override fun getRefreshKey(state: PagingState<Int, SpiritEntity>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpiritEntity> {
        val currentPage = params.key?: 1
        val response = repo.getSpiritList(currentPage, keywords)
        return if (response.isSuccess) {
            val base = response.getOrDefault(SpiritList())
            LoadResult.Page(base.data, null, if (base.data.isEmpty()) null else currentPage + 1)
        } else {
            val thr = response.exceptionOrNull()?:Throwable("unknow error")
            LoadResult.Error(thr)
        }
    }
}