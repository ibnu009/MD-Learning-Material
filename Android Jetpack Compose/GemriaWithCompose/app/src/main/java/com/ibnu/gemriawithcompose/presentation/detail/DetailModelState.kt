package com.ibnu.gemriawithcompose.presentation.detail

import com.ibnu.gemriawithcompose.data.model.Game

data class DetailModelState(
    val isAddedToCart: Boolean = false,
    val game: Game? = null,
){
    companion object {
        val Loading = DetailModelState()
    }
}