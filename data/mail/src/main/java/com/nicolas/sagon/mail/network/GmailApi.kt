package com.nicolas.sagon.mail.network

import com.nicolas.sagon.mail.model.MeThreadResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GmailApiService {
    @GET("gmail/v1/users/me/threads")
    suspend fun getMeThreads(
        @Query("maxResults") maxResults: Int = 10,
    ): MeThreadResponse

    @GET("gmail/v1/users/me/labels")
    suspend fun getMeLabels(
    ): MeThreadResponse
}