package com.ibnu.distory.utils.helper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibnu.distory.data.network.response.BasicResponse
import retrofit2.HttpException
import java.lang.reflect.Type

val gson = Gson()
val type: Type = object : TypeToken<BasicResponse>() {}.type

fun Exception.createResponse(): BasicResponse? {
    return when (this) {
        is HttpException -> {
            gson.fromJson(response()?.errorBody()?.charStream(), type)
        }
        else -> {
            BasicResponse(
                message = this.message ?: "",
                error = true
            )
        }
    }
}