package com.ibnu.distory.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("userId")
    val id: String,
    val name: String,
    val token: String,
)