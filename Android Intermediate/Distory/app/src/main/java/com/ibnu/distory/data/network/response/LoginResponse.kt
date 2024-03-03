package com.ibnu.distory.data.network.response

import com.google.gson.annotations.SerializedName
import com.ibnu.distory.data.model.User

data class LoginResponse(
    val error: Boolean,
    val message: String,
    @SerializedName("loginResult")
    val data: User
)