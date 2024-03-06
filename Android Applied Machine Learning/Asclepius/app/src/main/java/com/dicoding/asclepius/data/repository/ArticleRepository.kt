package com.dicoding.asclepius.data.repository

import com.dicoding.asclepius.data.model.Article
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.data.source.ArticleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ArticleRepository(
    private val dataSource: ArticleDataSource,
) {
    suspend fun getArticles(): Flow<ApiResponse<List<Article>>> =
        dataSource.fetchArticles().flowOn(Dispatchers.IO)
}