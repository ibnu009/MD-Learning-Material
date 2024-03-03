package com.ibnu.distory.presentation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.repository.StoryRepository
import com.ibnu.distory.utils.helper.Event
import kotlinx.coroutines.launch
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
        val uploadResult: LiveData<Event<ApiResponse<String>>> by lazy { _uploadResult }
    private val _uploadResult = MutableLiveData<Event<ApiResponse<String>>>()

    fun uploadStory(
        photo: File,
        description: String,
        latitude: Float?,
        longitude: Float?
    ) {
        viewModelScope.launch {
            repository.addStory(photo, description, latitude, longitude).collect {
                _uploadResult.postValue(Event(it))
            }
        }
    }

}