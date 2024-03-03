package com.ibnu.distory.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.ibnu.distory.data.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStories(users: List<Story>)
    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, Story>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}