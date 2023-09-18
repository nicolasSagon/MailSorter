package com.nicolas.sagon.authentication.event

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed interface AuthenticationEvents {
    class OnConnectActivityResult(val task: Task<GoogleSignInAccount>?): AuthenticationEvents
}