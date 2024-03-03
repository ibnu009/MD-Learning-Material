package com.ibnu.gemriawithcompose.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gemriawithcompose.data.model.Game
import com.ibnu.gemriawithcompose.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private var mIsAdded = false
    private val isAddedToCart: MutableStateFlow<Boolean> = MutableStateFlow(mIsAdded)
    private var gameDetailResult: MutableStateFlow<Game?> = MutableStateFlow(null)

    val detailModelState = combine(
        isAddedToCart,
        gameDetailResult,
    ) { isAdded, gameDetail ->
        DetailModelState(
            isAdded,
            gameDetail,
        )
    }

    fun onAddToCart() {
        viewModelScope.launch {
            mIsAdded = !mIsAdded
            isAddedToCart.value = mIsAdded
            repository.updateGameAddedToCartStatus(
                mIsAdded, gameDetailResult.value?.id ?: -1
            )
        }
    }

    fun retrieveGameDetail(id: Int) {
        viewModelScope.launch {
            val game = repository.getGameDetail(id)
            gameDetailResult.value = game
            mIsAdded = game.isAddedToCart
            isAddedToCart.value = mIsAdded
        }
    }

}