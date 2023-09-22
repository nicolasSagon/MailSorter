package com.nicolas.sagon.mailSorter.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nicolas.sagon.authentification.network.GoogleOauthApiService
import com.nicolas.sagon.mailSorter.di.qualifier.GoogleOAuth2Retrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

const val GOOGLE_OAUTH_API_BASE_URL = "https://oauth2.googleapis.com"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @GoogleOAuth2Retrofit
    @Provides
    @Singleton
    fun provideGoogleOauthRetrofit() = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(GOOGLE_OAUTH_API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideGoogleOauthApiService(
        @GoogleOAuth2Retrofit retrofit: Retrofit,
    ) = retrofit.create(GoogleOauthApiService::class.java)

}