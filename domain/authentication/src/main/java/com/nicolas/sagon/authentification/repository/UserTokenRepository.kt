package com.nicolas.sagon.authentification.repository

import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.model.UserTokens
import kotlinx.coroutines.flow.Flow

interface UserTokenRepository {
    fun getUserTokens(user: User): Flow<UserTokens>

    fun refreshUserToken(user: User): Flow<UserTokens>
}