package com.ibnu.gitfriend.data.source

import com.ibnu.gitfriend.data.local.dao.UserDao
import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.data.network.services.UserService
import com.ibnu.gitfriend.utils.RawQueryHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart
import timber.log.Timber
import java.io.File

class UserDataSource(private val userService: UserService, private val userDao: UserDao) {
    suspend fun fetchUsers(): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchUsers()
                userDao.insertAllUsers(response)
                val users = userDao.getAllUsers()
                emit(ApiResponse.Success(users))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun fetchUserDetail(username: String): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchUserDetail(username)
                if (userDao.isUserIsExist(username)){
                    userDao.insertUserDetail(
                        response.company ?: "-",
                        response.location ?: "-",
                        response.name ?: "-",
                        response.followers ?: 0,
                        response.following ?: 0,
                        username
                    )
                } else {
                    userDao.insertUser(response)
                }

                val user = userDao.getDetailUser(username)
                Timber.d("Fetched from local is ${user.username} and ${user.username}")
                emit(ApiResponse.Success(user))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun fetchUserFollowers(username: String): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchUserFollower(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun fetchUserFollowing(username: String): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchUserFollowing(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun fetchSearchUserResult(username: String): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = userService.fetchSearchUserResult(username)
                if (response.totalCount?.equals(0) != false) {
                    emit(ApiResponse.Empty)
                } else {
                    response.items?.let { userDao.insertAllUsers(it) }
                    val users =
                        userDao.searchUserByName(RawQueryHelper.searchUserQuery(username))
                    emit(ApiResponse.Success(users))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun updateUserFavorite(isFavorite: Boolean, id: Int) {
        userDao.updateUserFavoriteStatus(isFavorite, id)
    }

    suspend fun fetchFavoriteUsers(): Flow<ApiResponse<List<User>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val users = userDao.getAllFavoriteUsers()
                if (users.isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(users))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }
}