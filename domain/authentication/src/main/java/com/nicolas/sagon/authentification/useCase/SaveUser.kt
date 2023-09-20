package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.repository.UserRepository


class SaveUser(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        email: String,
        idToken: String,
        serverAuthCode: String,
        accessToken: String? = null,
        refreshToken: String? = null,
    ) {
        userRepository.saveUser(
            email = email,
            idToken = idToken,
            serverToken = serverAuthCode,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}