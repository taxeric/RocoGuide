package com.lanier.rocoguide.vm.repo

import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.EggGroupList

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/30 10:39
 * Desc  :
 */
class EggGroupRepo {

    suspend fun getEggGroup(): Result<EggGroupList>{
        return RetrofitHelper.launch { Net.service.getSpiritEggGroup() }
    }
}