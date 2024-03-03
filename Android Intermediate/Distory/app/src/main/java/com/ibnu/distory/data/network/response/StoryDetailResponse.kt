package com.ibnu.distory.data.network.response

import com.google.gson.annotations.SerializedName
import com.ibnu.distory.data.model.Story

data class StoryDetailResponse(
    val error: Boolean,
    val message: String,
    @SerializedName("story")
    val data: Story
)