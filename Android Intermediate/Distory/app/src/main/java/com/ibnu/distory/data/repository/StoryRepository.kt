package com.ibnu.distory.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ibnu.distory.data.model.Story
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.resource.StoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class StoryRepository(
    private val dataSource: StoryDataSource,
) {

    suspend fun getStories(isLocationEnabled: Boolean): Flow<ApiResponse<List<Story>>> =
        dataSource.fetchStories(isLocationEnabled).flowOn(Dispatchers.IO)

    suspend fun getStoryDetail(id: String): Flow<ApiResponse<Story>> =
        dataSource.fetchStoryDetail(id).flowOn(Dispatchers.IO)

    suspend fun addStory(
        photo: File,
        description: String,
        latitude: Float?,
        longitude: Float?
    ): Flow<ApiResponse<String>> =
        dataSource.addStory(photo, description, latitude, longitude).flowOn(Dispatchers.IO)

    fun getPagingStories(): LiveData<PagingData<Story>> {
        return dataSource.fetchPagingStories()

    }
}