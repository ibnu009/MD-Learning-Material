package com.ibnu.gemriawithcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gemriawithcompose.data.model.Game
import com.ibnu.gemriawithcompose.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GameRepository
) : ViewModel() {
    private var games: ArrayList<Game> = ArrayList()

    private var gameResult: MutableStateFlow<List<Game>> =
        MutableStateFlow(arrayListOf())
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")

    val homeModelState = combine(
        searchText,
        gameResult,
    ) { text, matchedUsers ->
        HomeModelState(
            text,
            matchedUsers,
        )
    }

    init {
        retrieveGames()
    }

    private fun retrieveGames() {
        viewModelScope.launch {
            games.clear()
            games.addAll(repository.getGames())
            gameResult.value = games
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            gameResult.value = games
            return
        }
        val gameFiltered = games.filter { x ->
            x.name.contains(changedSearchText, true)
        }
        gameResult.value = gameFiltered
    }

    fun onClearClick() {
        searchText.value = ""
        gameResult.value = games
    }

}