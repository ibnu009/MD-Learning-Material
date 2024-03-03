package com.ibnu.gitfriend.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ibnu.gitfriend.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(users: User)

    @Query("UPDATE user SET company = :company, location = :location, name = :realName, followers = :followers, following = :following WHERE login = :username")
    suspend fun insertUserDetail(
        company: String,
        location: String,
        realName: String,
        followers: Int,
        following: Int,
        username: String
    )

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :username)")
    suspend fun isUserIsExist(username : String) : Boolean

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE login = :username")
    suspend fun getDetailUser(username: String): User

    @Query("SELECT * FROM user WHERE is_favorite = 1")
    suspend fun getAllFavoriteUsers(): List<User>

    @RawQuery(observedEntities = [User::class])
    suspend fun searchUserByName(query: SupportSQLiteQuery): List<User>

    @Query("UPDATE user SET is_favorite = :isFav WHERE id = :id")
    suspend fun updateUserFavoriteStatus(isFav: Boolean, id: Int)
}