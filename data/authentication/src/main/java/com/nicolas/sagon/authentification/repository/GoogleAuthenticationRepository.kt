package com.nicolas.sagon.authentification.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.nicolas.sagon.authentification.model.GoogleSignInUser
import com.nicolas.sagon.authentification.model.User
import com.nicolas.sagon.authentification.model.toDomainModel

class GoogleAuthenticationRepository(val context: Context) :
    AuthenticationRepository {

    override fun getLastSignedIdUser(): User? {
        val gsa = GoogleSignIn.getLastSignedInAccount(context)
        return if (gsa != null && gsa.displayName != null && gsa.email != null && gsa.idToken != null) {
            GoogleSignInUser(
                userName = gsa.displayName!!,
                email = gsa.email!!,
                id = gsa.id!!,
                idToken = gsa.idToken!!
            ).toDomainModel()
        } else {
            null
        }
    }
}