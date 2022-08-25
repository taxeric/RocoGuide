package com.lanier.rocoguide.vm.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lanier.rocoguide.entity.Skill
import com.lanier.rocoguide.vm.repo.remote.SkillRemoteData
import com.lanier.rocoguide.vm.source.SkillSource
import kotlinx.coroutines.flow.Flow

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 9:56
 * Desc  :
 */
class SkillRepo {

    private val remoteData = SkillRemoteData()

    fun getAllSkill(): Flow<PagingData<Skill>> {
        return Pager(
            PagingConfig(20, prefetchDistance = 1)
        ){
            SkillSource(remoteData)
        }.flow
    }

    fun searchSkill(keywords: String = ""): Flow<PagingData<Skill>> {
        return Pager(
            PagingConfig(20, prefetchDistance = 1)
        ){
            SkillSource(remoteData, keywords)
        }.flow
    }
}