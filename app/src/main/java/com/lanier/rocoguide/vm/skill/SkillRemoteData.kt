package com.lanier.rocoguide.vm.skill

import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.net.Net

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/25 9:59
 * Desc  :
 */
class SkillRemoteData {

    suspend fun getSkillList(page: Int, keywords: String = "") = RetrofitHelper.launch {
        Net.service.getSkillsList(page, keywords)
    }
}