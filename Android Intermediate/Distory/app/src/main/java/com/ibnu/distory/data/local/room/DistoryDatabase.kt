package com.ibnu.distory.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibnu.distory.data.local.dao.RemoteKeysDao
import com.ibnu.distory.data.local.dao.StoryDao
import com.ibnu.distory.data.model.RemoteKeys
import com.ibnu.distory.data.model.Story

@Database(
    entities = [Story::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

abstract class DistoryDatabase : RoomDatabase() {
    abstract fun getStoryDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}