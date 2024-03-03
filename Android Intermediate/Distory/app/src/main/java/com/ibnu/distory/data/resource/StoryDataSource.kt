package com.ibnu.distory.data.resource

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ibnu.distory.data.local.room.DistoryDatabase
import com.ibnu.distory.data.mediator.StoryRemoteMediator
import com.ibnu.distory.data.model.Story
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.network.services.StoryService
import com.ibnu.distory.utils.helper.createResponse
import com.ibnu.distory.utils.helper.toMultipart
import com.ibnu.distory.utils.helper.toRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class StoryDataSource(private val database: DistoryDatabase, private val userService: StoryService) {

    fun fetchPagingStories(): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, userService),
            pagingSourceFactory = {
                database.getStoryDao().getAllStories()
            }
        ).liveData
    }

    suspend fun fetchStories(isLocationEnabled: Boolean = false): Flow<ApiResponse<List<Story>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchStories(
                    location = if(isLocationEnabled) 1 else 0
                )
                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                if (response.data.isEmpty()) {
                    emit(ApiResponse.Empty)
                    return@flow
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun fetchStoryDetail(id: String): Flow<ApiResponse<Story>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchStoryDetail(id)
                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }
                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun addStory(
        photo: File,
        description: String,
        latitude: Float?,
        longitude: Float?
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.addStory(
                    photo = photo.toMultipart(),
                    description = description.toRequestBody(),
                    latitude = latitude.toRequestBody(),
                    longitude = longitude.toRequestBody(),
                )

                if (response.error) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }
}