package com.ibnu.gemriawithcompose.data.repository

import com.ibnu.gemriawithcompose.data.local.dao.GameDao
import com.ibnu.gemriawithcompose.data.model.Game

class GameRepository(
    private val gameDao: GameDao
) {

    suspend fun insertAllGames(games:List<Game>) = gameDao.insertAllGames(games)

    suspend fun getGames() = gameDao.getAllGames()

    suspend fun getGameDetail(gameId: Int) = gameDao.getDetailGame(gameId)

    suspend fun getCartGames() = gameDao.getAllCartGames()

    suspend fun updateGameAddedToCartStatus(isAdded: Boolean, id: Int) =
        gameDao.updateGameCartStatus(isAdded, id)

}