package com.lanier.rocoguide.base.net

import com.lanier.rocoguide.entity.*
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Create by Eric
 * on 2022/7/29
 */
interface APIService {

    @GET("api/changeLog/v1/version")
    suspend fun getNewestVersion(): ChangeLogEntity

    @GET("api/changeLog/v1/bulletin")
    suspend fun getNewestBulletin(): ChangeLogEntity

    @GET("api/news/v1/news")
    suspend fun getNewsList(
        @Query("page") page: Int,
        @Query("amount") amount: Int = 20
    ): NewsList

    @GET("api/spirit/v1/spirits")
    suspend fun getSpiritList(
        @Query("page") page: Int,
        @Query("keywords") keywords: String = "",
        @Query("amount") amount: Int = 20
    ): SpiritList

    @GET("api/spirit/v1/spirit")
    suspend fun getSpiritById(
        @Query("id") id: Int
    ): SpiritDetailEntity

    @GET("api/skill/v1/skills")
    suspend fun getSkillsList(
        @Query("page") page: Int,
        @Query("keywords") keywords: String = "",
        @Query("amount") amount: Int = 20
    ): SkillsList

    @GET("api/group/v1/groups")
    suspend fun getSpiritEggGroup(): EggGroupList

    @Streaming
    @GET
    suspend fun getFileStream(
        @Url url: String
    ): ResponseBody
}