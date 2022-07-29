package com.lanier.lib_net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.nio.charset.Charset

/**
 * Create by Eric
 * on 2022/7/29
 */
class PrintInterceptor: Interceptor {
    
    private val TAG = "PrintInterceptor"
    
    private fun String.log(){
        Log.i(TAG, this)
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())
        val mediaType = response.body()!!.contentType()
        val content = response.body()!!.string()
        "Request Start--------------------------".log()
        "请求地址: ${request.url()}".log()
        if ("POST" == request.method()) {
            if (request.body() != null) {
               "请求类型: ${request.method()}, ContentLength = (${request.body()!!.contentLength()}-byte body)".log()
            }
        } else {
            "请求类型 = ${request.method()}".log()
        }
        //打印请求头
        val heards = request.headers().names()
        for (names in heards) {
            for (name in request.headers(names)) {
                "请 求 头: $names = $name".log()
            }
        }
        if ("POST" == request.method()) {
            if (request.body() != null) {
                val buffer = Buffer()
                request.body()!!.writeTo(buffer)

                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = request.body()!!.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                "请求参数: ${buffer.readString(charset!!)}".log()
            }
        }
        "请求响应: ${response.code()}".log()
        "响应结果: $content".log()
        "--------------------Request End: ".log()
        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }
}