package com.nicolas.sagon.mailSorter.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nicolas.sagon.authentification.network.GoogleOauthApiService
import com.nicolas.sagon.authentification.useCase.GetUser
import com.nicolas.sagon.mail.network.GmailApiService
import com.nicolas.sagon.mailSorter.di.qualifier.GmailRetrofit
import com.nicolas.sagon.mailSorter.di.qualifier.GoogleOAuth2Retrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

const val GOOGLE_OAUTH_API_BASE_URL = "https://oauth2.googleapis.com"
const val GMAIL_API_BASE_URL = "https://gmail.googleapis.com"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @GoogleOAuth2Retrofit
    fun provideGoogleOauthRetrofit() = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(GOOGLE_OAUTH_API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideGoogleOauthApiService(
        @GoogleOAuth2Retrofit retrofit: Retrofit,
    ) = retrofit.create(GoogleOauthApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        getUser: GetUser,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                runBlocking {
                    val user = getUser()
                    val request = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${user?.accessToken}")
                        .build()
                    it.proceed(request)
                }
            })
            .build()
    }

    @Provides
    @Singleton
    @GmailRetrofit
    fun provideGmailRetrofit(
        okHttpClient: OkHttpClient,
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(GMAIL_API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideGmailApiService(
        @GmailRetrofit retrofit: Retrofit,
    ) = retrofit.create(GmailApiService::class.java)
}