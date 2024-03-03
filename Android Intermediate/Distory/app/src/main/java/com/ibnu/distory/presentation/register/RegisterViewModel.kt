package com.ibnu.distory.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.repository.AuthRepository
import com.ibnu.distory.utils.helper.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    val registerResult: LiveData<Event<ApiResponse<String>>> by lazy { _registerResult }
    private val _registerResult = MutableLiveData<Event<ApiResponse<String>>>()

    fun registerUser(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.register(name, email, password)
                .collect {
                    _registerResult.postValue(Event(it))
                }
        }
    }

}