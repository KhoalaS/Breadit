package com.khoalas.breadit.viemodel.state

sealed class LoginState {
    data object LOGGED_IN: LoginState()
    data object LOGGED_OUT: LoginState()
    data object LOADING: LoginState()
}