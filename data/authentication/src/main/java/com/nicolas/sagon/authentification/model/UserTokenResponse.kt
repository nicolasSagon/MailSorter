package com.nicolas.sagon.authentification.model

import com.nicolas.sagon.authentification.error.UserHasEmptyRefreshTokenException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTokenResponse(
    @SerialName(value = "access_token")
    val accessToken: String,

    @SerialName(value = "expires_in")
    val expiresIn: Long,

    val scope: String,

    @SerialName(value = "token_type")
    val tokenType: String,

    @SerialName(value = "id_token")
    val idToken: String,

    @SerialName(value = "refresh_token")
    val refreshToken: String? = null,
)

fun UserTokenResponse.toDomainModel(): UserTokens {
    return object : UserTokens {
        override val accessToken: String
            get() = this@toDomainModel.accessToken
        override val refreshToken: String
            get() {
                return this@toDomainModel.refreshToken ?: throw UserHasEmptyRefreshTokenException(
                    idToken = this@toDomainModel.idToken,
                    accessToken = this@toDomainModel.accessToken
                )
            }
        override val idToken: String
            get() = this@toDomainModel.idToken


    }
}
