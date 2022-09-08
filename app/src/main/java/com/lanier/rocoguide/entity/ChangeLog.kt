package com.lanier.rocoguide.entity

/**
 * Create by Eric
 * on 2022/7/30
 */
data class ChangeLogEntity(
    val code: Int = 0,
    val `data`: ChangeLogData = ChangeLogData(),
    val msg: String = ""
)

data class ChangeLogData(
    val createTime: String = "",
    val id: Int = 0,
    val log: String = "",
    val logType: Int = 0,
    val md5: String = "",
    val mandatory: Boolean = false,
    val size: String = "",
    val url: String = "",
    val versionCode: Int = 0,
    val versionName: String = "",
    val isNewestVersion: Boolean = false,
    val isDownloading: Boolean = false,
    val hasDownloads: Boolean = false,
    val filename: String = ""
)