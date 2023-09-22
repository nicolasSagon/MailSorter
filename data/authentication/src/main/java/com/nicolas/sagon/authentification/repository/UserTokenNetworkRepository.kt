package com.nicolas.sagon.authentification.repository

import com.nicolas.sagon.authentification.error.UserHasEmptyRefreshTokenException
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration
import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.model.UserTokens
import com.nicolas.sagon.authentification.model.toDomainModel
import com.nicolas.sagon.authentification.network.GoogleOauthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserTokenNetworkRepository(
    private val googleOauthApiService: GoogleOauthApiService,
    private val googleSignInConfiguration: GoogleSignInConfiguration,
) : UserTokenRepository {

    override fun getUserTokens(user: User): Flow<UserTokens> {
        return flow {
            emit(
                googleOauthApiService.getTokens(
                    clientId = googleSignInConfiguration.googleSignInClientId,
                    clientSecret = googleSignInConfiguration.googleSignInClientSecret,
                    idToken = user.idToken,
                    serverToken = user.serverToken,
                )
            )
        }.map {
            it.toDomainModel()
        }
    }

    override fun refreshUserToken(user: User): Flow<UserTokens> {
        return flow {
            user.refreshToken?.let {
                emit(
                    googleOauthApiService.refreshTokens(
                        clientId = googleSignInConfiguration.googleSignInClientId,
                        clientSecret = googleSignInConfiguration.googleSignInClientSecret,
                        refreshToken = it
                    )
                )
            } ?: error(UserHasEmptyRefreshTokenException())
        }.map {
            it.toDomainModel()
        }
    }
}