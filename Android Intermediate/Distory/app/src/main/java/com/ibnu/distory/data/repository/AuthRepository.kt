package com.ibnu.distory.data.repository

import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.network.request.LoginRequest
import com.ibnu.distory.data.network.request.RegisterRequest
import com.ibnu.distory.data.resource.AuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(
    private val dataSource: AuthDataSource,
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        val request = RegisterRequest(
            name = name,
            email = email,
            password = password
        )
        return dataSource.register(request).flowOn(Dispatchers.IO)
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        val request = LoginRequest(
            email = email,
            password = password
        )
        return dataSource.login(request).flowOn(Dispatchers.IO)
    }
}