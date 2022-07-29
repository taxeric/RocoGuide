package com.lanier.rocoguide.vm.repo

import com.lanier.lib_net.RetrofitHelper
import com.lanier.rocoguide.base.net.Net
import com.lanier.rocoguide.entity.NewsList

/**
 * Create by Eric
 * on 2022/7/29
 */
class NewsRepo {

    suspend fun getNewsList(page: Int): Result<NewsList> {
        return RetrofitHelper.launch { Net.service.getNewsList(page) }
    }
}