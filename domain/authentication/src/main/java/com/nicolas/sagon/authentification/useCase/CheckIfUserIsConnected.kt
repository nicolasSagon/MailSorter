package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.repository.UserRepository


class CheckIfUserIsConnected(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.loadUser() != null
    }
}