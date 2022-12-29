package com.lanier.rocoguide.vm.egggroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.cache.LocalCache
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.GeneticSpiritData
import com.lanier.rocoguide.entity.SpiritEggGroup
import com.lanier.rocoguide.utils.copyStreamToFile
import com.lanier.rocoguide.utils.defaultLocalJsonDataPath
import com.lanier.rocoguide.utils.logE
import com.lanier.rocoguide.utils.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 15:11
 * Desc  :
 */
class GeneticViewModel: ViewModel() {

    val currentGeneticList = MutableStateFlow<List<GeneticSpiritData>>(emptyList())

    fun getGeneticData(group: SpiritEggGroup) {
        currentGeneticList.tryEmit(emptyList())
        val url = "${RetrofitHelper.baseUrl}res/other/html/${buildUrl(group.id)}"
        "url -> $url".logI()
        val path = File(defaultLocalJsonDataPath)
        if (!path.exists()){
            path.mkdir()
        }
        val outputFile = File(defaultLocalJsonDataPath, "${group.name}.html")
        if (outputFile.exists()){
            analyzeData(outputFile, group.id.toString())
        } else {
            downloadGeneticFile(url, outputFile, group)
        }
    }

    private fun analyzeData(file: File, id: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                LocalCache.initSpiritDataById(file, id) {
                    if (it) {
                        currentGeneticList.tryEmit(LocalCache.getGeneticDataById(id))
                    } else {
                        "解析failed".logE()
                    }
                }
            }
        }
    }

    private fun downloadGeneticFile(
        url: String,
        outputFile: File,
        group: SpiritEggGroup
    ) {
        viewModelScope.launch {
            val responseBody = withContext(Dispatchers.Default) {
                Net.service.getFileStream(url)
            }
            withContext(Dispatchers.IO) {
                "延时2s start".logI()
                delay(2000L)
                "延时2s end--".logI()
                "size -> ${responseBody.contentLength()}".logI()
                try {
                    responseBody.byteStream().copyStreamToFile(outputFile)
                    if (outputFile.exists()) {
                        LocalCache.initSpiritDataById(outputFile, group.id.toString()) {
                            if (it) {
                                currentGeneticList.tryEmit(LocalCache.getGeneticDataById(group.id.toString()))
                            } else {
                                "解析failed".logE()
                            }
                        }
                    }
                } catch (e: Exception) {
                    "download failed -> ${e.message}".logE()
                }
            }
        }
    }

    private fun buildUrl(id: Int): String = when (id) {
        2 -> "group_plant.html"
        3 -> "group_spirit.html"
        4 -> "group_sky.html"
        5 -> "group_immortal.html"
        6 -> "group_clever.html"
        7 -> "group_guard.html"
        8 -> "group_power.html"
        9 -> "group_ground.html"
        10 -> "group_animal.html"
        else -> ""
    }
}