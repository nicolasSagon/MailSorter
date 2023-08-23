package com.nicolas.sagon.authentification.model

data class GoogleSignInUser(
    val userName: String,
    val email: String,
    val id: String,
    val idToken: String
)

fun GoogleSignInUser.toDomainModel(): User {
    return object : User {
        override val userName: String
            get() = this@toDomainModel.userName
        override val email: String
            get() = this@toDomainModel.email
        override val id: String
            get() = this@toDomainModel.id
        override val idToken: String
            get() = this@toDomainModel.idToken
    }
}