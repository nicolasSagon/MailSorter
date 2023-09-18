package com.nicolas.sagon.authentication.viewModel

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nicolas.sagon.authentication.event.AuthenticationEvents
import com.nicolas.sagon.authentication.state.AuthenticationState
import com.nicolas.sagon.authentification.useCase.RetrieveLastConnectedUser
import com.nicolas.sagon.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "AuthenticationViewModel"

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val retrieveLastConnectedUser: RetrieveLastConnectedUser,
) : BaseViewModel<AuthenticationEvents>() {
    private val _uiState: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Loading)
    val uiState: StateFlow<AuthenticationState> = _uiState

    init {
        val user = retrieveLastConnectedUser()
        _uiState.value = if (user == null) {
            AuthenticationState.NotConnected
        } else {
            AuthenticationState.UserConnected(user)
        }
    }

    override fun onUserEvent(event: AuthenticationEvents) {
        when(event) {
            is AuthenticationEvents.OnConnectActivityResult -> handleConnectActivityResult(event.task)
        }
    }

    private fun handleConnectActivityResult(task: Task<GoogleSignInAccount>?) {
        try {
            val gsa = task?.getResult(ApiException::class.java)

            if (gsa != null) {
                Log.i(TAG, "User is connected : ${gsa.email}; ${gsa.displayName}")
                val user = retrieveLastConnectedUser()
                _uiState.value = if (user == null) {
                    AuthenticationState.Error("User not connected")
                } else {
                    AuthenticationState.UserConnected(user)
                }
            } else {
                AuthenticationState.Error("Error with the google sign in method")
            }
        } catch (e: ApiException) {
            Log.e(TAG, "Error in AuthScreen", e)
            AuthenticationState.Error("Error with the google sign in method")
        }
    }
}