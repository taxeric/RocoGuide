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
    val createTime: String = "",
    val title: String = "",
    val type: Int = 1,
    val updateTime: String = "",
    val url: String = "",
    val externalContent: String = ""
)

data class WrapNews(
    val date: String = "",
    val data: List<NewsData> = listOf(),
)
