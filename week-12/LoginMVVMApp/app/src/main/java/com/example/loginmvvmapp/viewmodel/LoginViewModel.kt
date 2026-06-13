package com.example.loginmvvmapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginmvvmapp.data.local.entity.User
import com.example.loginmvvmapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var loginState: String = ""
        private set

    fun insertDummyUser() {
        viewModelScope.launch {
            repository.insert(
                User(
                    username = "aika",
                    password = "12345"
                )
            )
        }
    }

    fun login(
        username: String,
        password: String,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            val user = repository.login(username, password)

            loginState = if (user != null) {
                "Login berhasil!"
            } else {
                "Username atau password salah"
            }

            onResult(loginState)
        }
    }
}