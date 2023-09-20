package com.nicolas.sagon.authentication.activityResultContract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration

class GoogleApiSignInContract(
    private val googleSignInConfiguration: GoogleSignInConfiguration,
) : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleSignInConfiguration.googleSignInClientId)
            .requestServerAuthCode(googleSignInConfiguration.googleSignInClientId)
            .requestEmail()
            .requestScopes(Scope("https://mail.google.com/"))
            .build()

        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> {
                GoogleSignIn.getSignedInAccountFromIntent(intent)
            }

            else -> null
        }
    }

}