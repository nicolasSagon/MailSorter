package com.nicolas.sagon.mailSorter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.nicolas.sagon.authentification.crypto.CryptoManager
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration
import com.nicolas.sagon.authentification.model.UserDatastore
import com.nicolas.sagon.authentification.serializer.UserDatastoreSerializer
import com.nicolas.sagon.mailSorter.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConfigurationModule {

    @Provides
    @Singleton
    fun provideGoogleSignInConfiguration(@ApplicationContext context: Context) =
        GoogleSignInConfiguration(
            googleSignInClientId = context.getString(R.string.GOOGLE_OAUTH_CLIENT_ID),
            googleSignInClientSecret = context.getString(R.string.GOOGLE_OAUTH_CLIENT_SECRET)
        )

    @Provides
    @Singleton
    fun provideCryptoManager() = CryptoManager()

    @Provides
    @Singleton
    fun provideUserDatastoreSerializer(
        cryptoManager: CryptoManager,
    ) = UserDatastoreSerializer(cryptoManager)

    @Provides
    @Singleton
    fun provideUserDatastore(
        @ApplicationContext context: Context,
        userDatastoreSerializer: UserDatastoreSerializer,
    ): DataStore<UserDatastore> {
        return DataStoreFactory.create(
            serializer = userDatastoreSerializer,
            produceFile = { context.dataStoreFile("user.json") },
            corruptionHandler = null,
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}