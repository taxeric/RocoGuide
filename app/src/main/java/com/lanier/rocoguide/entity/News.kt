package com.lanier.rocoguide.entity

import kotlinx.serialization.Serializable

/**
 * Create by Eric
 * on 2022/7/25
 */
data class NewsList(
    val code: Int = 0,
    val msg: String = "",
    val `data`: List<NewsData> = emptyList(),
    val total: Int = 0
)

@Serializable
data class NewsData(
    val content: String = "",
    val create_time: Long = 0L,
    val title: String = "",
    val type: Int = 1,
    val update_time: Long = 0L,
    val url: String = ""
)
