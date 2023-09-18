package com.nicolas.sagon.authentication.state

sealed class AuthenticationState {
    object Loading : AuthenticationState()

    object NotConnected : AuthenticationState()

    data class Error(
        val errorMessage: String,
    ) : AuthenticationState()
}