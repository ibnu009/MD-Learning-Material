package com.ibnu.distory.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.distory.data.model.Story
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.repository.StoryRepository
import kotlinx.coroutines.launch

class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    val storyResult: LiveData<ApiResponse<Story>> by lazy { _storyResult }
    private val _storyResult = MutableLiveData<ApiResponse<Story>>()

    fun getStoryDetail(id: String) {
        viewModelScope.launch {
            repository.getStoryDetail(id).collect {
                _storyResult.postValue(it)
            }
        }
    }
}