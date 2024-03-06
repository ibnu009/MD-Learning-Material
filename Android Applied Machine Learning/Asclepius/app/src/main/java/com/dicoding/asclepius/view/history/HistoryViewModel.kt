package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.model.History
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    val historyResult: LiveData<ApiResponse<List<History>>> by lazy { _historyResult }
    private val _historyResult = MutableLiveData<ApiResponse<List<History>>>()

    fun getHistories() {
        viewModelScope.launch {
            historyRepository.getHistories().collect {
                _historyResult.postValue(it)
            }
        }
    }
}