package com.ibnu.gemriawithcompose.data.local.dao

import androidx.room.*
import com.ibnu.gemriawithcompose.data.model.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllGames(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<Game>

    @Query("SELECT * FROM game WHERE id = :gameId")
    suspend fun getDetailGame(gameId: Int): Game

    @Query("SELECT * FROM game WHERE is_added_to_cart = 1")
    suspend fun getAllCartGames(): List<Game>

    @Query("UPDATE game SET is_added_to_cart = :isAdded WHERE id = :id")
    suspend fun updateGameCartStatus(isAdded: Boolean, id: Int)
}