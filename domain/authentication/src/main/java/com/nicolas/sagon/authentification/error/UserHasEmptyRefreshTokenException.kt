package com.nicolas.sagon.authentification.error

class UserHasEmptyRefreshTokenException(
    val idToken: String,
    val accessToken: String,
) :
    Exception()