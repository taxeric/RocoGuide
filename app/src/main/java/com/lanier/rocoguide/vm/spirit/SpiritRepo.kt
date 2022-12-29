package com.lanier.rocoguide.vm.spirit

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lanier.rocoguide.entity.SpiritEntity
import kotlinx.coroutines.flow.Flow

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:05
 * Desc  :
 */
class SpiritRepo {

    private val remoteData = SpiritRemoteData()

    fun getAllSpirit(): Flow<PagingData<SpiritEntity>>{
        return Pager(
            PagingConfig(20, prefetchDistance = 3),
        ) {
            SpiritSource(remoteData)
        }.flow
    }

    fun searchSpirit(keywords: String = ""): Flow<PagingData<SpiritEntity>>{
        return Pager(
            PagingConfig(20, prefetchDistance = 3),
        ) {
            SpiritSource(remoteData, keywords)
        }.flow
    }

    suspend fun searchSpiritById(id: Int) = remoteData.getSpiritById(id)
}