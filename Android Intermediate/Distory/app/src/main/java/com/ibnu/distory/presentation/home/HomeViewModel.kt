package com.ibnu.distory.presentation.home

import androidx.lifecycle.ViewModel
import com.ibnu.distory.data.repository.StoryRepository

class HomeViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getStory() = repository.getPagingStories()
}