package com.dicoding.asclepius.data.network.services

import com.dicoding.asclepius.data.model.Article
import com.dicoding.asclepius.data.network.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("q") country: String = "cancer",
        @Query("category") category: String = "health",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String
    ): BaseResponse<List<Article>>
}