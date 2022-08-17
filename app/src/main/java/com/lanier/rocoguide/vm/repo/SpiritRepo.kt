package com.lanier.rocoguide.vm.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.vm.repo.remote.SpiritRemoteData
import com.lanier.rocoguide.vm.source.SpiritSource
import kotlinx.coroutines.flow.Flow

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:05
 * Desc  :
 */
class SpiritRepo {

    private val remoteRepo = SpiritRemoteData()

    fun getAllSpirit(): Flow<PagingData<SpiritEntity>>{
        return Pager(
            PagingConfig(20, prefetchDistance = 3),
        ) {
            SpiritSource(remoteRepo)
        }.flow
    }

    fun searchSpirit(keywords: String = ""): Flow<PagingData<SpiritEntity>>{
        return Pager(
            PagingConfig(20, prefetchDistance = 3),
        ) {
            SpiritSource(remoteRepo, keywords)
        }.flow
    }

    suspend fun searchSpiritById(id: Int) = remoteRepo.getSpiritById(id)
}