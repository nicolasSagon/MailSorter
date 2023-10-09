package com.nicolas.sagon.authentification.model

interface User {
    val email: String
    val idToken: String
    val serverToken: String
    val accessToken: String?
    val refreshToken: String?
}

fun User.isConnectedUser(): Boolean {
    return email.isNotEmpty() && idToken.isNotEmpty() && serverToken.isNotEmpty() && !accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()
}

fun User.formattedString(): String {
    return "User : {\n" +
            "\temail: ${this.email},\n" +
            "\tidToken: ${this.idToken},\n" +
            "\tserverToken: ${this.serverToken},\n" +
            "\taccessToken: ${this.accessToken},\n" +
            "\trefreshToken: ${this.refreshToken},\n" +
            "}"
}