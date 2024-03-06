package com.dicoding.asclepius.data.source

import com.dicoding.asclepius.data.local.dao.HistoryDao
import com.dicoding.asclepius.data.model.History
import com.dicoding.asclepius.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryDataSource(private val historyDao: HistoryDao) {
    suspend fun fetchHistories(): Flow<ApiResponse<List<History>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = historyDao.getAllHistories()
                if (response.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun addHistory(history: History) {
        historyDao.insertHistory(history)
    }
}