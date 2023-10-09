package com.nicolas.sagon.mail.repository

import com.nicolas.sagon.mail.error.InvalidAccessTokenException
import com.nicolas.sagon.mail.model.UserThreads
import com.nicolas.sagon.mail.model.toDomainModel
import com.nicolas.sagon.mail.network.GmailApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class MailRepositoryRemoteImp(
    private val gmailApiService: GmailApiService,
) : MailRepository {
    override fun getUserThreads(): Flow<UserThreads> {
        return flow {
            emit(
                gmailApiService.getMeThreads()
            )
        }.catch {
            if (it is HttpException && it.code() == 401) {
                throw InvalidAccessTokenException()
            } else {
                throw it
            }
        }.map { it.toDomainModel() }

    }
}