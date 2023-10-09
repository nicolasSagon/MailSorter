package com.nicolas.sagon.mailSorter.di

import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.repository.UserTokenRepository
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.GetUser
import com.nicolas.sagon.authentification.useCase.GetUserAccessToken
import com.nicolas.sagon.authentification.useCase.RefreshUserTokens
import com.nicolas.sagon.authentification.useCase.RevokeUserAccessToken
import com.nicolas.sagon.authentification.useCase.SaveUser
import com.nicolas.sagon.mail.repository.MailRepository
import com.nicolas.sagon.mail.useCase.GetUserMailThreads
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    fun provideCheckIfUserIsConnectedUseCase(
        userRepository: UserRepository,
    ): CheckIfUserIsConnected {
        return CheckIfUserIsConnected(userRepository)
    }

    @Provides
    fun provideSaveUserUseCase(
        userRepository: UserRepository,
    ): SaveUser {
        return SaveUser(userRepository)
    }

    @Provides
    fun provideGetUserAccessTokenUseCase(
        userTokenRepository: UserTokenRepository,
        userRepository: UserRepository,
    ): GetUserAccessToken {
        return GetUserAccessToken(
            tokenRepository = userTokenRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideRefreshUserAccessToken(
        userTokenRepository: UserTokenRepository,
        userRepository: UserRepository,
    ): RefreshUserTokens {
        return RefreshUserTokens(
            tokenRepository = userTokenRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideRevokeUserAccessToken(
        userTokenRepository: UserTokenRepository,
        userRepository: UserRepository,
    ): RevokeUserAccessToken {
        return RevokeUserAccessToken(
            tokenRepository = userTokenRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideGetUser(
        userRepository: UserRepository,
    ): GetUser {
        return GetUser(
            userRepository = userRepository
        )
    }

    @Provides
    fun provideGetUserMailThreads(
        userRepository: UserRepository,
        mailRepository: MailRepository,
        refreshUserTokens: RefreshUserTokens,
    ): GetUserMailThreads = GetUserMailThreads(
        userRepository = userRepository,
        mailRepository = mailRepository,
        refreshUserTokens = refreshUserTokens
    )
}