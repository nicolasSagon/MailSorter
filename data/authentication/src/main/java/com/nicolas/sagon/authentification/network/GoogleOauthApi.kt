package com.nicolas.sagon.authentification.network

import com.nicolas.sagon.authentification.model.UserTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GoogleOauthApiService {
    @FormUrlEncoded
    @POST("token")
    suspend fun getTokens(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("id_token") idToken: String,
        @Field("code") serverToken: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("access_type") accessType: String = "offline",
    ): UserTokenResponse

    @FormUrlEncoded
    @POST("token")
    suspend fun refreshTokens(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token",
    ): UserTokenResponse

    @FormUrlEncoded
    @POST("revoke")
    suspend fun revokeUserToken(
        @Field("token") token: String
    ): Unit
}