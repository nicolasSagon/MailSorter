package com.nicolas.sagon.authentification.model

interface UserTokens {
    val accessToken: String
    val refreshToken: String
    val idToken: String
}