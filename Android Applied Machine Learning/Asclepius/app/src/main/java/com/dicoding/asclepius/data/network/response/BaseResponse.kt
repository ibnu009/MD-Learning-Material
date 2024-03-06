package com.dicoding.asclepius.data.network.response

data class BaseResponse<out T> (
    val status: String?,
    val totalResults: Int?,
    val articles: T,
)
