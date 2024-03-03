package com.ibnu.gitfriend.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object RawQueryHelper {
    fun searchUserQuery(username: String): SimpleSQLiteQuery{
        return SimpleSQLiteQuery("SELECT * FROM user WHERE login LIKE '%$username%'")
    }
}