package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.repository.AuthenticationRepository


class RetrieveLastConnectedUser(
    private val authenticationRepository: AuthenticationRepository,
) {
    operator fun invoke(): User? {
        return authenticationRepository.getLastSignedIdUser()
    }
}