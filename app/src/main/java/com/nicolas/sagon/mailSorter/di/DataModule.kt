package com.nicolas.sagon.mailSorter.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.nicolas.sagon.authentification.model.UserDatastore
import com.nicolas.sagon.authentification.repository.AuthenticationRepository
import com.nicolas.sagon.authentification.repository.GoogleAuthenticationRepository
import com.nicolas.sagon.authentification.repository.UserDatastoreRepository
import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.RetrieveLastConnectedUser
import com.nicolas.sagon.authentification.useCase.SaveUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        @ApplicationContext context: Context,
    ): AuthenticationRepository {
        return GoogleAuthenticationRepository(context = context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        dataStore: DataStore<UserDatastore>,
    ): UserRepository {
        return UserDatastoreRepository(dataStore)
    }

    @Provides
    fun provideAuthenticationUseCase(
        authenticationRepository: AuthenticationRepository,
    ): RetrieveLastConnectedUser {
        return RetrieveLastConnectedUser(authenticationRepository)
    }

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

    // Provide Retrofit

}