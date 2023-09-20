package com.nicolas.sagon.authentification.repository

import com.nicolas.sagon.authentification.model.UserBis

interface UserRepository {
    suspend fun saveUser(
        email: String,
        idToken: String,
        serverToken: String,
        accessToken: String?,
        refreshToken: String?,
    )

    suspend fun loadUser(): UserBis?
}