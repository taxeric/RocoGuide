package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/30
 */
data class UpdateEntity(
    val log: String = "",
    val createTime: Long = 0L,
    val mandatory: Int = 0,
    val url: String = "",
    val size: Long = 0L,
    val versionCode: Int = 0,
    val versionName: String = "",
    val type: Int = -1
)
