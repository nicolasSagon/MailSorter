package com.nicolas.sagon.mailSorter.di

import android.content.Context
import com.nicolas.sagon.authentification.repository.AuthenticationRepository
import com.nicolas.sagon.authentification.repository.GoogleAuthenticationRepository
import com.nicolas.sagon.authentification.useCase.RetrieveLastConnectedUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AuthenticationModule {

    @Provides
    fun provideAuthenticationRepository(
        @ApplicationContext context: Context,
    ): AuthenticationRepository {
        return GoogleAuthenticationRepository(context = context)
    }

    @Provides
    fun provideAuthenticationUseCase(
        authenticationRepository: AuthenticationRepository,
    ): RetrieveLastConnectedUser {
        return RetrieveLastConnectedUser(authenticationRepository)
    }
}