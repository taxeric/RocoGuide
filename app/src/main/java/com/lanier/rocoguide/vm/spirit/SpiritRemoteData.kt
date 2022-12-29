package com.lanier.rocoguide.vm.spirit

import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.net.Net

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/8 14:55
 * Desc  :
 */
class SpiritRemoteData {

    suspend fun getSpiritList(page: Int, keywords: String = "") = RetrofitHelper.launch {
        Net.service.getSpiritList(page, keywords)
    }

    suspend fun getSpiritById(id: Int) = RetrofitHelper.launch {
        Net.service.getSpiritById(id)
    }
}