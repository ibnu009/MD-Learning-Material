package com.ibnu.gitfriend.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibnu.gitfriend.data.local.dao.UserDao
import com.ibnu.gitfriend.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)

abstract class GitFriendDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}