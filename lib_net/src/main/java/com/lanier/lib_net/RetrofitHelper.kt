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
    private lateinit var mRetrofit: Retrofit

    var baseUrl: String = ""

    fun initHelper(baseUrl: String){
        this.baseUrl = baseUrl
        if (!::mRetrofit.isInitialized){
            mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(defaultClient)
                .build()
        }
    }

    fun <S> createService(clazz: Class<S>): S = mRetrofit.create(clazz)

    suspend fun <T> launch(block: suspend () -> T): Result<T>{
        return try {
            val response = withContext(Dispatchers.Default) {
                block()
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun <T> launch(
        block: suspend () -> T,
        result: (Result<T>) -> Unit,
        complete: () -> Unit = {}
    ){
        withContext(Dispatchers.Default) {
            try {
                val t = block()
                result(Result.success(t))
            } catch (e: Exception) {
                result(Result.failure(e))
            } finally {
                withContext(Dispatchers.Main) {
                    complete()
                }
            }
        }
    }
}
