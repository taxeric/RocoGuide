package com.lanier.rocoguide.base.net

import com.lanier.rocoguide.entity.NewsList
import com.lanier.rocoguide.entity.SkillsList
import com.lanier.rocoguide.entity.SpiritDetailEntity
import com.lanier.rocoguide.entity.SpiritList
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

    @GET("api/news/v1/page")
    suspend fun getNewsList(
        @Query("page") page: Int,
        @Query("amount") amount: Int = 20
    ): NewsList

    @GET("api/genius/v1/page")
    suspend fun getSpiritList(
        @Query("page") page: Int,
        @Query("keywords") keywords: String = "",
        @Query("amount") amount: Int = 20
    ): SpiritList

    @GET("api/genius/v1/genius")
    suspend fun getSpiritById(
        @Query("id") id: Int
    ): SpiritDetailEntity

    @GET("api/skill/v1/page")
    suspend fun getSkillsList(
        @Query("page") page: Int,
        @Query("keywords") keywords: String = "",
        @Query("amount") amount: Int = 20
    ): SkillsList

    @Streaming
    @GET
    suspend fun getFileStream(
        @Url url: String
    ): ResponseBody
}