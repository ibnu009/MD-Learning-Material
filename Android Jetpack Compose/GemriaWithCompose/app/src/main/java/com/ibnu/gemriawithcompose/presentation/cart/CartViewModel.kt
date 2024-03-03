package com.ibnu.gemriawithcompose.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gemriawithcompose.data.model.Game
import com.ibnu.gemriawithcompose.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: GameRepository
) : ViewModel() {
    val gameResult: MutableStateFlow<List<Game>> =
        MutableStateFlow(arrayListOf())

    fun retrieveCartGames() {
        viewModelScope.launch {
            gameResult.value = repository.getCartGames()
        }
    }
}