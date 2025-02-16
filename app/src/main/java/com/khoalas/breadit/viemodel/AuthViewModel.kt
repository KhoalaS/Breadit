package com.khoalas.breadit.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoalas.breadit.auth.AuthRepository
import com.khoalas.breadit.viemodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.LOADING)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        viewModelScope.launch {
            repository.authToken.collect { token ->
                if (token == null) {
                    _loginState.value = LoginState.LOGGED_OUT
                } else if (token.isEmpty()) {
                    _loginState.value = LoginState.LOADING
                } else {
                    _loginState.value = LoginState.LOGGED_IN
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.login(username, password)
        }
    }
}