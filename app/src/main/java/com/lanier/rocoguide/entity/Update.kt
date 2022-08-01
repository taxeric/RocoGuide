package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/30
 */
data class UpdateEntity(
    val code: Int,
    val `data`: UpdateData,
    val msg: String
)

data class UpdateData(
    val createTime: String,
    val id: Int,
    val log: String,
    val logType: Int,
    val md5: String,
    val mandatory: Boolean,
    val size: String,
    val url: String,
    val versionCode: Int,
    val versionName: String
)