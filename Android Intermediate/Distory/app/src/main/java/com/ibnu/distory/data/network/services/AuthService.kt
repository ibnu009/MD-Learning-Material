package com.ibnu.distory.data.network.services

import com.ibnu.distory.data.network.request.LoginRequest
import com.ibnu.distory.data.network.request.RegisterRequest
import com.ibnu.distory.data.network.response.BasicResponse
import com.ibnu.distory.data.network.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): BasicResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

}