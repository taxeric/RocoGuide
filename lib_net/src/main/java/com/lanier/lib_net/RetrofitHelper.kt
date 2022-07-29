package com.lanier.lib_net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

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

    fun initHelper(baseUrl: String){
        if (!::mRetrofit.isInitialized){
            mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(defaultClient)
                .build()
        }
    }

    fun <S> createService(clazz: Class<S>) = mRetrofit.create(clazz)

    suspend fun <T> launch(
        block: suspend () -> T,
        complete: () -> Unit = {}
    ){
        withContext(Dispatchers.Default) {
            try {
                val t = block()
                Result.success(t)
            } catch (e: Exception) {
                Result.failure(e)
            } finally {
                withContext(Dispatchers.Main) {
                    complete()
                }
            }
        }
    }
}
