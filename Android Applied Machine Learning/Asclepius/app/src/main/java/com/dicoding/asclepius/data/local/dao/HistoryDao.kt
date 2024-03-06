package com.dicoding.asclepius.data.local.dao

import androidx.room.*
import com.dicoding.asclepius.data.model.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(users: History)

    @Query("SELECT * FROM History")
    suspend fun getAllHistories(): List<History>

    @Query("SELECT * FROM History WHERE id = :id")
    suspend fun getDetailHistory(id: Int): History
}