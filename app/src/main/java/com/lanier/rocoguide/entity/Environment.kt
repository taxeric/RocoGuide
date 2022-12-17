package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/12/17
 */
data class EnvironmentList(
    val code: Int = 0,
    val `data`: List<EnvironmentEntity> = listOf(),
    val msg: String = "",
    val total: Int = 0
)

data class EnvironmentEntity(
    val effects: String = "",
    val icon: String = "",
    val id: Int = 0,
    val introduce: String = "",
    val name: String = "",
    val type: Int = 0
)
