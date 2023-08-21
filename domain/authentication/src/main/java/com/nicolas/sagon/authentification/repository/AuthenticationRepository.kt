package com.nicolas.sagon.authentification.repository

import com.nicolas.sagon.authentification.model.User

interface AuthenticationRepository {
    fun getLastSignedIdUser(): User?
}