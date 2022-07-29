package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/25
 */
data class NewsList(
    val code: Int = 0,
    val msg: String = "",
    val `data`: List<NewsData> = emptyList(),
    val totalItem: Int = 0
)

data class NewsData(
    val content: String = "",
    val create_time: Long = 0L,
    val title: String = "",
    val type: Int = 1,
    val update_time: Long = 0L,
    val url: String = "https://mp.weixin.qq.com/s/ebY1ZpcDpnEJVbR40Uortg"
)
