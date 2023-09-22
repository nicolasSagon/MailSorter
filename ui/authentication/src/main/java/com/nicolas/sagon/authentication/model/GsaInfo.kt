package com.nicolas.sagon.authentication.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

data class GsaInfo(val email: String, val idToken: String, val serverAuthCode: String)

fun GoogleSignInAccount.safeInfo(): GsaInfo? {
    return GsaInfo(
        email = email ?: return null,
        idToken = idToken ?: return null,
        serverAuthCode = serverAuthCode ?: return null
    )
}