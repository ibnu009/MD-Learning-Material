package com.dicoding.asclepius.data.repository

import com.dicoding.asclepius.data.model.History
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.data.source.HistoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HistoryRepository(
    private val dataSource: HistoryDataSource,
) {
    suspend fun getHistories(): Flow<ApiResponse<List<History>>> =
        dataSource.fetchHistories().flowOn(Dispatchers.IO)

    suspend fun addHistory(history: History) {
        dataSource.addHistory(history)
    }
}