package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/12/17
 */
data class LineageList(
    val code: Int = 0,
    val `data`: List<LineageEntity> = listOf(),
    val msg: String = "",
    val total: Int = 0
)

data class LineageEntity(
    val icon: String = "",
    val id: Int = 0,
    val introduce: String = "",
    val name: String = ""
)
