package com.dicoding.asclepius.view.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.model.Article
import com.dicoding.asclepius.data.model.History
import com.dicoding.asclepius.data.network.ApiResponse
import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    private val articleRepository: ArticleRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    val articleResult: LiveData<ApiResponse<List<Article>>> by lazy { _articleResult }
    private val _articleResult = MutableLiveData<ApiResponse<List<Article>>>()

    fun getArticles() {
        viewModelScope.launch {
            articleRepository.getArticles().collect {
                _articleResult.postValue(it)
            }
        }
    }

    fun addHistory(label: String, confidence: String, imageBase64: String) {
        viewModelScope.launch {
            val history =
                History(
                    id = 0,
                    label = label,
                    confidence = confidence,
                    imageBase64 = imageBase64
                )
            historyRepository.addHistory(history)
        }
    }
}