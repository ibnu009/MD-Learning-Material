package com.ibnu.gitfriend.data.repository

import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val dataSource: UserDataSource,
) {



    suspend fun getUsers(): Flow<ApiResponse<List<User>>> =
        dataSource.fetchUsers().flowOn(Dispatchers.IO)

    suspend fun getUserDetail(username: String): Flow<ApiResponse<User>> =
        dataSource.fetchUserDetail(username).flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<User>>> =
        dataSource.fetchUserFollowers(username).flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<User>>> =
        dataSource.fetchUserFollowing(username).flowOn(Dispatchers.IO)

    suspend fun getSearchResult(username: String): Flow<ApiResponse<List<User>>> =
        dataSource.fetchSearchUserResult(username).flowOn(Dispatchers.IO)

    suspend fun updateUserFavoriteStatus(isFavorite: Boolean, id: Int) =
        dataSource.updateUserFavorite(isFavorite, id)

    suspend fun getFavoriteUsers() = dataSource.fetchFavoriteUsers()

}