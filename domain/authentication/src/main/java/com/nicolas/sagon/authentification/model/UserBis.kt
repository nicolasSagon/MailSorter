package com.nicolas.sagon.authentification.model

interface UserBis {
    val email: String
    val idToken: String
    val serverToken: String
    val accessToken: String?
    val refreshToken: String?
}