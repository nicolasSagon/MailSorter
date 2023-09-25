package com.nicolas.sagon.authentication.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nicolas.sagon.authentication.event.AuthenticationEvents
import com.nicolas.sagon.authentication.model.safeInfo
import com.nicolas.sagon.authentication.state.AuthenticationState
import com.nicolas.sagon.authentification.error.UserHasEmptyRefreshTokenException
import com.nicolas.sagon.authentification.model.GoogleSignInConfiguration
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.GetUserAccessToken
import com.nicolas.sagon.authentification.useCase.RevokeUserAccessToken
import com.nicolas.sagon.authentification.useCase.SaveUser
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.core.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AuthenticationViewModel"

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val navigateToScreen: NavigateToScreen,
    private val checkIfUserIsConnected: CheckIfUserIsConnected,
    private val saveUser: SaveUser,
    private val getUserAccessToken: GetUserAccessToken,
    private val revokeUserAccessToken: RevokeUserAccessToken,
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

            gsa?.safeInfo()?.let {
                handleUserConnection(it.email, it.idToken, it.serverAuthCode)
            } ?: run {
                _uiState.value = AuthenticationState.Error("Error with the google sign in method")
            }
        } catch (e: ApiException) {
            Log.e(TAG, "Error in AuthScreen", e)
            _uiState.value = AuthenticationState.Loading
        }
    }

    private fun handleUserConnection(email: String, idToken: String, serverAuthCode: String) {
        viewModelScope.launch {
            try {
                saveUser(
                    email = email,
                    idToken = idToken,
                    serverAuthCode = serverAuthCode
                )
                _uiState.value = AuthenticationState.Loading
                getUserAccessToken().collectLatest {
                    navigateToScreen(Screen.HomeScreen)
                }
            } catch (e: UserHasEmptyRefreshTokenException) {
                Log.e(TAG, "Error when handling user connection", e)
                // @TODO : Need to send a message to the UI to display snackbar for the error and that the user need to retry
                _uiState.value = AuthenticationState.Error("Empty refresh token need to destroy google session")
                revokeUserAccessToken().collectLatest {}
            }
            catch (e: Exception) {
                Log.e(TAG, "Error when handling user connection", e)
                _uiState.value = AuthenticationState.Error("Error when handling user connection")
            }
        }
    }
}