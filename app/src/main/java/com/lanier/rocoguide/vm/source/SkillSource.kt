package com.lanier.rocoguide.vm.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.entity.SkillsList
import com.lanier.rocoguide.vm.repo.remote.SkillRemoteData

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 10:04
 * Desc  :
 */
class SkillSource(
    private val repo: SkillRemoteData,
    private val keywords: String = ""
): PagingSource<Int, Skill>() {
    override fun getRefreshKey(state: PagingState<Int, Skill>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Skill> {
        val currentPage = params.key?: 1
        val response = repo.getSkillList(currentPage, keywords)
        return if (response.isSuccess) {
            val base = response.getOrDefault(SkillsList())
            LoadResult.Page(base.data, null, if (base.data.isEmpty()) null else currentPage + 1)
        } else {
            val thr = response.exceptionOrNull()?: Throwable("unknow error")
            LoadResult.Error(thr)
        }
    }
}