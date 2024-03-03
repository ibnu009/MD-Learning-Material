package com.ibnu.gitfriend.data.network

import com.ibnu.gitfriend.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(
    private val requestHeaders: HashMap<String, String>,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.toString()
        print("<<<<<<<<< $path")
        if (path.contains("logout")) {
            mapRequestHeaders()
            print("<<<<<<<<< $requestHeaders")
        } else {
            mapRequestHeaders()
        }

        val request = mapHeaders(chain)

        return chain.proceed(request)
    }

    private fun mapRequestHeaders() {
        println("<<<<<<<<< Before : $requestHeaders")
        val token = BuildConfig.TOKEN
        requestHeaders["Authorization"] = "token $token"
        println("<<<<<<<<< After $requestHeaders")
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }

}