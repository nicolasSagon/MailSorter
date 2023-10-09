package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.repository.UserRepository


class GetUser(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.loadUser()
}