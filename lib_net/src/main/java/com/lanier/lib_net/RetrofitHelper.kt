package com.lanier.lib_net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create by Eric
 * on 2022/7/29
 */
object RetrofitHelper {

    private val defaultClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(PrintInterceptor())
        .build()
    private val defaultNoPrintClient = OkHttpClient()
        .newBuilder().build()

    private lateinit var mRetrofit: Retrofit
    private lateinit var mRetrofitWithoutPrint: Retrofit
    private var initialized = false

    var baseUrl: String = ""

    fun initHelper(baseUrl: String){
        initialized = true
        this.baseUrl = baseUrl
        if (!::mRetrofit.isInitialized){
            mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultClient)
                .build()
        }
        if (!::mRetrofitWithoutPrint.isInitialized){
            mRetrofitWithoutPrint = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultNoPrintClient)
                .build()
        }
    }

    fun <S> createService(clazz: Class<S>, withPrint: Boolean = true): S = if (withPrint)
        mRetrofit.create(clazz)
    else
        mRetrofitWithoutPrint.create(clazz)

    suspend fun <T> launch(block: suspend () -> T): Result<T>{
        if (!initialized || baseUrl.isEmpty()) {
            return Result.failure(Throwable("unknown host"))
        }
        return try {
            val response = withContext(Dispatchers.Default) {
                block()
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
