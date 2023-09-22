package com.nicolas.sagon.mailSorter.di

import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.repository.UserTokenRepository
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.GetUserAccessToken
import com.nicolas.sagon.authentification.useCase.SaveUser
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

}