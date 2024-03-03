package com.ibnu.gemriawithcompose.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibnu.gemriawithcompose.data.local.dao.GameDao
import com.ibnu.gemriawithcompose.data.model.Game

@Database(
    entities = [Game::class],
    version = 1,
    exportSchema = false
)

abstract class GameriaDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao
}