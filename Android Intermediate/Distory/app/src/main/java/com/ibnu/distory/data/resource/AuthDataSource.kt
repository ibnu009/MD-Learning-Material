package com.ibnu.distory.data.resource

import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.network.request.LoginRequest
import com.ibnu.distory.data.network.request.RegisterRequest
import com.ibnu.distory.data.network.services.AuthService
import com.ibnu.distory.di.networkModule
import com.ibnu.distory.di.preferenceModule
import com.ibnu.distory.utils.PreferenceManager
import com.ibnu.distory.utils.helper.createResponse
import com.ibnu.distory.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class AuthDataSource(
    private val service: AuthService, private val pref: PreferenceManager
) {
    suspend fun register(
        request: RegisterRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.register(request)
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

    suspend fun login(
        request: LoginRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                wrapEspressoIdlingResource{
                    val response = service.login(request)
                    if (response.error) {
                        emit(ApiResponse.Error(response.message))
                        return@flow
                    }

                    pref.storeLoginData(response.data.token)
                    reloadModule()
                    emit(ApiResponse.Success(response.message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    /**
     * reloadModule() is a required to remove previous preference and network module and
     * replace it with newer one. It is only required in login to insert or renew token.
     **/
    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)

        unloadKoinModules(networkModule)
        loadKoinModules(networkModule)
    }
}