package com.example.searchresources.data.remote

import com.example.searchresources.data.model.SearchImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRemoteDataSource {
    @GET("/v2/search/image")
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80,
    ): SearchImageResponse
}