package com.lanier.rocoguide.vm.spirit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.lanier.rocoguide.base.RocoEventModel
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.FilterSpiritEntity
import com.lanier.rocoguide.entity.SeriesEntity
import com.lanier.rocoguide.entity.SeriesList
import com.lanier.rocoguide.entity.SpiritEntity
import com.lanier.rocoguide.utils.defaultLocalJsonDataPath
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:08
 * Desc  :
 */
class SpiritViewModel: ViewModel() {

    private val _spiritMainListFlow = MutableStateFlow<PagingData<SpiritEntity>>(PagingData.empty())
    val spiritMainListFlow get() = _spiritMainListFlow
    private val remoteData = SpiritRemoteData()

    fun getSpirit(series: Int = 1) {
        viewModelScope.launch {
            Pager(
                PagingConfig(20, prefetchDistance = 3),
            ) {
                SpiritSource(remoteData, series = series)
            }.flow
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _spiritMainListFlow.tryEmit(pagingData)
            }
        }
    }
}

val SeriesFlow = MutableSharedFlow<RocoEventModel<Nothing>>(replay = 1)
val FilterFlow = MutableSharedFlow<FilterSpiritEntity>(replay = 1)

suspend fun getSeries() {
    LocalCache.seriesList.clear()
    SeriesFlow.tryEmit(RocoEventModel.Loading)
    LocalCache.seriesList.addAll(readSeries())
    if (LocalCache.seriesList.isEmpty()) {
        val series = try {
            Net.service.getSeries()
        } catch (e: Throwable) {
            SeriesList()
        }
        LocalCache.seriesList.clear()
        LocalCache.seriesList.addAll(series.data)
        if (series.data.isNotEmpty()) {
            LocalCache.curSeriesIndex = 0
            LocalCache.filterSpiritEntity = LocalCache.filterSpiritEntity.copy(
                series = series.data[0]
            )
            saveSeries(series)
        }
        SeriesFlow.tryEmit(RocoEventModel.Complete())
    } else {
        LocalCache.curSeriesIndex = 0
        SeriesFlow.tryEmit(RocoEventModel.Complete())
    }
}

private fun readSeries(): List<SeriesEntity> {
    val gson = Gson()
    val file = File(defaultLocalJsonDataPath, "series.json")
    if (!file.exists()) {
        return emptyList()
    }
    val str = file.readText()
    if (str.isEmpty()) {
        return emptyList()
    }
    return gson.fromJson(str, SeriesList::class.java).data
}

private fun saveSeries(entity: SeriesList) {
    val gson = Gson()
    val file = File(defaultLocalJsonDataPath, "series.json")
    val parentFile = File(defaultLocalJsonDataPath)
    if (!parentFile.exists()){
        parentFile.mkdir()
    }
    if (!file.exists()){
        file.createNewFile()
    }
    file.writeText(gson.toJson(entity))
}