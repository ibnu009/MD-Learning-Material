package com.ibnu.gemriawithcompose.presentation.home

import com.ibnu.gemriawithcompose.data.model.Game

data class HomeModelState(
    val searchText: String = "",
    val games: List<Game> = arrayListOf(),
) {

    companion object {
        val Empty = HomeModelState()
    }

}