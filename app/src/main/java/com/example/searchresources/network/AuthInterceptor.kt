package com.example.searchresources.network

import com.example.searchresources.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                BuildConfig.KAKAO_API_KEY
            ).build()

        return chain.proceed(newRequest)
    }
}