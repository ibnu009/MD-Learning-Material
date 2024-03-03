package com.ibnu.gemriawithcompose.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gemriawithcompose.data.repository.GameRepository
import com.ibnu.gemriawithcompose.data.source.GameDataSource
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: GameRepository
) : ViewModel() {

    init {
        insertGames()
    }

    private fun insertGames() {
        viewModelScope.launch {
            repository.insertAllGames(GameDataSource.listGame)
        }
    }
}