package com.khoalas.breadit.viemodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoalas.breadit.auth.AuthRepository
import com.khoalas.breadit.viemodel.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    val authToken: StateFlow<String?> = repository.authToken
    private val _loginState = mutableStateOf<LoginState>(LoginState.LOADING)
    val loginState: State<LoginState>
        get() = _loginState

    init {
        viewModelScope.launch {
            authToken.collect { token ->
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