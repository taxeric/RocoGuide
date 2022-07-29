package com.lanier.rocoguide.base.net

import com.lanier.lib_net.RetrofitHelper

/**
 * Create by Eric
 * on 2022/7/29
 */
object Net {

    val service by lazy {
        RetrofitHelper.createService(APIService::class.java)
    }
}