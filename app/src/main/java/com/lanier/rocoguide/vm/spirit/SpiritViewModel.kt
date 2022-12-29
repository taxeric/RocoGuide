package com.lanier.rocoguide.vm.spirit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.RocoEventModel
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.base.net.APIService
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.SeriesEntity
import com.lanier.rocoguide.entity.SeriesList
import com.lanier.rocoguide.utils.defaultLocalJsonDataPath
import com.lanier.rocoguide.utils.downloadFile
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 10:08
 * Desc  :
 */
class SpiritViewModel: ViewModel() {

    private val repo = SpiritRepo()

    val spiritMainListFlow = repo.getAllSpirit().cachedIn(viewModelScope)
}

val SeriesFlow = MutableSharedFlow<RocoEventModel<Nothing>>(replay = 1)

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
            saveSeries(series)
        }
        SeriesFlow.tryEmit(RocoEventModel.Complete())
    } else {
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
    file.writeText(gson.toJson(entity))
}