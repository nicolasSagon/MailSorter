package com.nicolas.sagon.authentification.repository

import com.nicolas.sagon.authentification.model.User

interface UserRepository {
    suspend fun saveUser(
        email: String,
        idToken: String,
        serverToken: String,
        accessToken: String?,
        refreshToken: String?,
    )

    suspend fun updateUserTokens(
        idToken: String,
        accessToken: String,
        refreshToken: String
    )

    suspend fun loadUser(): User?

    suspend fun deleteUser()
}