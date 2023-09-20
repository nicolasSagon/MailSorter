package com.nicolas.sagon.authentication.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nicolas.sagon.authentication.event.AuthenticationEvents
import com.nicolas.sagon.authentication.state.AuthenticationState
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.RetrieveLastConnectedUser
import com.nicolas.sagon.authentification.useCase.SaveUser
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AuthenticationViewModel"

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val retrieveLastConnectedUser: RetrieveLastConnectedUser,
    private val navigateToScreen: NavigateToScreen,
    private val checkIfUserIsConnected: CheckIfUserIsConnected,
    private val saveUser: SaveUser,
    val googleSignInConfiguration: GoogleSignInConfiguration,
) : BaseViewModel<AuthenticationEvents>() {
    private val _uiState: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Loading)
    val uiState: StateFlow<AuthenticationState> = _uiState

    init {
        viewModelScope.launch {
            val isUserConnected = checkIfUserIsConnected()
            if (isUserConnected) {
                navigateToScreen(Screen.HomeScreen)
            } else {
                _uiState.value = AuthenticationState.NotConnected
            }
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

            if (gsa != null &&
                gsa.email != null &&
                gsa.idToken != null &&
                gsa.serverAuthCode != null
            ) {
                viewModelScope.launch {
                    saveUser(
                        email = gsa.email!!,
                        idToken = gsa.idToken!!,
                        serverAuthCode = gsa.serverAuthCode!!
                    )
                    Log.i(TAG, "Token = ${gsa.idToken}")
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