package com.dicoding.asclepius.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.dao.HistoryDao
import com.dicoding.asclepius.data.model.History

@Database(
    entities = [History::class],
    version = 1,
    exportSchema = false
)

abstract class AsclepiusDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}