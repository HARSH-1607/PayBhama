package com.paybhama.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paybhama.app.data.model.User
import com.paybhama.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableLiveData<Result<User>>()
    val authState: LiveData<Result<User>> = _authState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.registerUser(email, password, name)
            _authState.value = result
            _isLoading.value = false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.loginUser(email, password)
            _authState.value = result
            _isLoading.value = false
        }
    }
}
