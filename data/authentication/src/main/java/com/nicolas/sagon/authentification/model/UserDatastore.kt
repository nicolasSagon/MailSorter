package com.nicolas.sagon.authentification.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDatastore(
    val email: String? = null,
    val idToken: String? = null,
    val serverToken: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
) {
    fun isFullUser(): Boolean {
        return email != null && idToken != null && serverToken != null
    }
}

fun UserDatastore.toDomainModel(): UserBis {
    return object : UserBis {
        override val email: String
            get() = this@toDomainModel.email!!
        override val idToken: String
            get() = this@toDomainModel.idToken!!
        override val serverToken: String
            get() = this@toDomainModel.serverToken!!
        override val accessToken: String?
            get() = this@toDomainModel.accessToken
        override val refreshToken: String?
            get() = this@toDomainModel.refreshToken
    }
}