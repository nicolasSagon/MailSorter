package com.nicolas.sagon.authentication.state

import com.nicolas.sagon.authentification.model.User

sealed class AuthenticationState {
    object Loading : AuthenticationState()

    data class UserConnected(
        val user: User,
    ) : AuthenticationState()

    object NotConnected: AuthenticationState()

    data class Error(
        val errorMessage: String,
    ): AuthenticationState()
}