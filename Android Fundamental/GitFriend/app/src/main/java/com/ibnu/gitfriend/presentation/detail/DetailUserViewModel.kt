package com.ibnu.gitfriend.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.gitfriend.data.model.User
import com.ibnu.gitfriend.data.network.ApiResponse
import com.ibnu.gitfriend.data.repository.UserRepository
import kotlinx.coroutines.launch

class DetailUserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val userDetailResult: LiveData<ApiResponse<User>> by lazy { _userDetailResult }
    private val _userDetailResult = MutableLiveData<ApiResponse<User>>()

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            repository.getUserDetail(username).collect {
                _userDetailResult.postValue(it)
            }
        }
    }

    fun changeFavoriteUserStatus(isFavorite: Boolean, id: Int){
        viewModelScope.launch {
            repository.updateUserFavoriteStatus(isFavorite, id)
        }
    }

}