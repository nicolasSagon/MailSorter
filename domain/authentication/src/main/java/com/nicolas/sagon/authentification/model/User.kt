package com.nicolas.sagon.authentification.model

interface User {
    val userName: String
    val email: String
    val id: String
    val idToken: String
}