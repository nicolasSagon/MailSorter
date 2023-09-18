package com.nicolas.sagon.authentication.viewModel

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nicolas.sagon.authentication.event.AuthenticationEvents
import com.nicolas.sagon.authentication.state.AuthenticationState
import com.nicolas.sagon.authentification.useCase.RetrieveLastConnectedUser
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val TAG = "AuthenticationViewModel"

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val retrieveLastConnectedUser: RetrieveLastConnectedUser,
    private val navigateToScreen: NavigateToScreen,
) : BaseViewModel<AuthenticationEvents>() {
    private val _uiState: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Loading)
    val uiState: StateFlow<AuthenticationState> = _uiState

    init {
        val user = retrieveLastConnectedUser()
        if (user == null) {
            _uiState.value = AuthenticationState.NotConnected
        } else {
            navigateToScreen(Screen.HomeScreen)
        }
    }

    override fun onUserEvent(event: AuthenticationEvents) {
        when (event) {
            is AuthenticationEvents.OnConnectActivityResult -> handleConnectActivityResult(event.task)
        }
    }

    private fun handleConnectActivityResult(task: Task<GoogleSignInAccount>?) {
        try {
            val gsa = task?.getResult(ApiException::class.java)

            if (gsa != null) {
                Log.i(TAG, "User is connected : ${gsa.email}; ${gsa.displayName}")
                val user = retrieveLastConnectedUser()
                if (user == null) {
                    _uiState.value = AuthenticationState.Error("User not connected")
                } else {
                    navigateToScreen(Screen.HomeScreen)
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