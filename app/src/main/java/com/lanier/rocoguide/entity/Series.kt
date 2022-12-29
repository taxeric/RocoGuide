package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/12/29
 */
data class SeriesList(
    val code: Int = 0,
    val `data`: List<SeriesEntity> = listOf(),
    val msg: String = "",
    val total: Int = 0
)

data class SeriesEntity(
    val id: Int = 0,
    val name: String = ""
)
