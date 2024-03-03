package com.ibnu.gitfriend.data.network.response

import com.google.gson.annotations.SerializedName
import com.ibnu.gitfriend.data.model.User

data class UserSearchResponse (
    @SerializedName("total_count")
    val totalCount: Int?,
    val items: List<User>?,
)
