package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.error.UserNotConnectedException
import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.repository.UserTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RevokeUserAccessToken(
    private val userRepository: UserRepository,
    private val tokenRepository: UserTokenRepository,
) {
    @OptIn(FlowPreview::class)
    operator fun invoke(): Flow<Unit> {
        return flow {
            emit(userRepository.loadUser())
        }.flatMapConcat {
            it?.let {
                tokenRepository.deleteUserSession(it)
            } ?: throw UserNotConnectedException()
        }.map {
            userRepository.deleteUser()
        }.flowOn(Dispatchers.IO)
    }
}