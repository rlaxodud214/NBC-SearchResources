package com.example.searchresources.network

import com.example.searchresources.BuildConfig
import com.example.searchresources.data.remote.SearchRemoteDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = BuildConfig.KAKAO_API_URL

    private val okHttpClient by lazy {
        val authTokenInHeader = AuthInterceptor()

        OkHttpClient.Builder()
            .addInterceptor(authTokenInHeader)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val search: SearchRemoteDataSource by lazy {
        retrofit.create(SearchRemoteDataSource::class.java)
    }
}