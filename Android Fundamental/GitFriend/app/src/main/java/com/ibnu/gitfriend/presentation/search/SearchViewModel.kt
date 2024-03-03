package com.ibnu.gitfriend.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.data.repository.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val userResult: LiveData<ApiResponse<List<User>>> by lazy { _userResult }
    private val _userResult = MutableLiveData<ApiResponse<List<User>>>()

    fun searchUser(username: String) {
        viewModelScope.launch {
            repository.getSearchResult(username).collect {
                _userResult.postValue(it)
            }
        }
    }

    fun changeFavoriteUserStatus(isFavorite: Boolean, id: Int){
        viewModelScope.launch {
            repository.updateUserFavoriteStatus(isFavorite, id)
        }
    }

}