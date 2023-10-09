package com.nicolas.sagon.mail.useCase

import com.nicolas.sagon.authentification.error.UserNotConnectedException
import com.nicolas.sagon.authentification.model.isConnectedUser
import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.useCase.RefreshUserTokens
import com.nicolas.sagon.core.retryOnceWithRefreshToken
import com.nicolas.sagon.mail.error.InvalidAccessTokenException
import com.nicolas.sagon.mail.model.UserThreads
import com.nicolas.sagon.mail.repository.MailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserMailThreads(
    private val userRepository: UserRepository,
    private val mailRepository: MailRepository,
    private val refreshUserTokens: RefreshUserTokens,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<UserThreads> {
        return flow {
            emit(userRepository.loadUser())
        }.flatMapConcat {
            it?.isConnectedUser()?.let { isConnectedUser ->
                if (isConnectedUser) {
                    mailRepository.getUserThreads()
                } else {
                    throw UserNotConnectedException()
                }
            } ?: throw UserNotConnectedException()
        }.retryOnceWithRefreshToken<UserThreads, InvalidAccessTokenException> {
            refreshUserTokens()
        }.flowOn(Dispatchers.IO)
    }
}