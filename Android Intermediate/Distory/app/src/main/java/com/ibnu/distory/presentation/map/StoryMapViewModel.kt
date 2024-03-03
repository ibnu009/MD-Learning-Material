package com.ibnu.distory.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.distory.data.model.Story
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.repository.StoryRepository
import kotlinx.coroutines.launch

class StoryMapViewModel(private val repository: StoryRepository) : ViewModel() {

    val storyResult: LiveData<ApiResponse<List<Story>>> by lazy { _storyResult }
    private val _storyResult = MutableLiveData<ApiResponse<List<Story>>>()

    fun getRecentStories() {
        viewModelScope.launch {
            repository.getStories(true).collect {
                _storyResult.postValue(it)
            }
        }
    }
}