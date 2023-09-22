package com.nicolas.sagon.mailSorter.di

import androidx.datastore.core.DataStore
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration
import com.nicolas.sagon.authentification.model.UserDatastore
import com.nicolas.sagon.authentification.network.GoogleOauthApiService
import com.nicolas.sagon.authentification.repository.UserDatastoreRepository
import com.nicolas.sagon.authentification.repository.UserRepository
import com.nicolas.sagon.authentification.repository.UserTokenNetworkRepository
import com.nicolas.sagon.authentification.repository.UserTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        dataStore: DataStore<UserDatastore>,
    ): UserRepository {
        return UserDatastoreRepository(dataStore)
    }

    @Provides
    @Singleton
    fun provideUserTokenRepository(
        googleOauthApiService: GoogleOauthApiService,
        googleSignInConfiguration: GoogleSignInConfiguration,
    ): UserTokenRepository {
        return UserTokenNetworkRepository(
            googleOauthApiService = googleOauthApiService,
            googleSignInConfiguration = googleSignInConfiguration
        )
    }

}