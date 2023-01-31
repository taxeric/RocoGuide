package com.lanier.rocoguide.vm.spirit

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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

    val eventFlow = MutableStateFlow(false)
    val list = mutableStateListOf<SpiritEntity>()
    var lastSeries = 1
    private var page = 0

    fun getSpirit(series: Int = 1, refresh: Boolean = true) {
        if (refresh || lastSeries != series) {
            page = 1
        } else {
            page ++
        }
        viewModelScope.launch {
            eventFlow.tryEmit(true)
            val response = Net.service.getSpiritList(page, keywords = "", seriesId = series)
            if (response.code == 200) {
                if (refresh || lastSeries != series) {
                    list.clear()
                }
                list.addAll(response.data)
            }
            lastSeries = series
            eventFlow.tryEmit(false)
        }
    }
}

val SeriesFlow = MutableSharedFlow<RocoEventModel<Nothing>>(replay = 1)
val FilterFlow = MutableSharedFlow<FilterSpiritEntity>(extraBufferCapacity = 1)

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