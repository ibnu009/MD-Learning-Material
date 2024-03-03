package com.ibnu.distory.data.network.services

import com.ibnu.distory.data.network.response.BasicResponse
import com.ibnu.distory.data.network.response.StoriesResponse
import com.ibnu.distory.data.network.response.StoryDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryService {

    @GET("stories")
    suspend fun fetchStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = 0
    ): StoriesResponse

    @GET("stories/{id}")
    suspend fun fetchStoryDetail(
        @Path("id") id: String
    ): StoryDetailResponse

    @POST("stories")
    @Multipart
    suspend fun addStory(
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") latitude: RequestBody? = null,
        @Part("lon") longitude: RequestBody? = null,
        ): BasicResponse

}