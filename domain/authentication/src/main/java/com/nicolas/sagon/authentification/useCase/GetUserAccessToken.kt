package com.nicolas.sagon.authentification.useCase

import com.nicolas.sagon.authentification.error.UserNotConnectedException
import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.repository.UserTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetUserAccessToken(
    private val userRepository: UserRepository,
    private val tokenRepository: UserTokenRepository,
) {
    @OptIn(FlowPreview::class)
    operator fun invoke(): Flow<User> {
        return flow {
            emit(userRepository.loadUser())
        }.flatMapConcat {
            it?.let {
                tokenRepository.getUserTokens(it)
            } ?: throw UserNotConnectedException()
        }.map {
            userRepository.updateUserTokens(
                idToken = it.idToken,
                refreshToken = it.refreshToken,
                accessToken = it.accessToken
            )
        }.map {
            userRepository.loadUser() ?: throw UserNotConnectedException()
        }.flowOn(Dispatchers.IO)
    }
}