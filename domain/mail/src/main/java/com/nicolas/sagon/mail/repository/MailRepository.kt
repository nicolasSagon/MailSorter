package com.nicolas.sagon.mail.repository

import com.nicolas.sagon.mail.model.UserThreads
import kotlinx.coroutines.flow.Flow

interface MailRepository {
    fun getUserThreads(): Flow<UserThreads>
}