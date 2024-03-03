package com.ibnu.gitfriend.data.network.services

import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.data.network.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// BASE URL DI TEMPAT LAIN
//END POINT
interface UserService {
    @GET("users")
    suspend fun fetchUsers(): List<User>

    @GET("users/{username}")
    suspend fun fetchUserDetail(
        @Path("username") username: String
    ): User

    @GET("search/users")
    suspend fun fetchSearchUserResult(
        @Query("q") username: String
    ): UserSearchResponse

    @GET("users/{username}/followers")
    suspend fun fetchUserFollower(
        @Path("username") username: String
    ): List<User>

    @GET("users/{username}/following")
    suspend fun fetchUserFollowing(
        @Path("username") username: String
    ): List<User>

}