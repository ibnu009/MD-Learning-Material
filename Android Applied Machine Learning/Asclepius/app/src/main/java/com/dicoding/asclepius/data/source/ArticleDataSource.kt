package com.dicoding.asclepius.data.source

import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.model.Article
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.data.network.services.ArticleService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleDataSource(private val articleService: ArticleService) {
    suspend fun fetchArticles(): Flow<ApiResponse<List<Article>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = articleService.fetchTopHeadlines(
                    country = "cancer",
                    category = "health",
                    language = "en",
                    apiKey = BuildConfig.API_KEY
                )
                if (response.articles.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response.articles))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}