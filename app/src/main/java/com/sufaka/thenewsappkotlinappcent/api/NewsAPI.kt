package com.sufaka.thenewsappkotlinappcent.api

import com.sufaka.thenewsappkotlinappcent.models.NewsResponse
import com.sufaka.thenewsappkotlinappcent.util.Constants.Companion.API_KEY
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): retrofit2.Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): retrofit2.Response<NewsResponse>

}