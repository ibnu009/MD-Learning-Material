package com.ibnu.gitfriend.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @SerializedName("avatar_url")
    @ColumnInfo("avatar_url")
    val avatarUrl: String?,
    val url: String?,
    val type: String?,
    @ColumnInfo("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("login")
    @ColumnInfo("login")
    val username: String?,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    @ColumnInfo("twitter_username")
    val twitterUsername: String?,
    @ColumnInfo("public_repos")
    val publicRepos: Int?,
    @ColumnInfo("public_gists")
    val publicGists: Int?,
    @SerializedName("followers")
    val followers: Int?,
    val following: Int?,
    @ColumnInfo("is_favorite")
    var isFavorite: Boolean = false
)
