package com.nicolas.sagon.authentication.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicolas.sagon.authentication.activityResultContract.GoogleApiSignInContract
import com.nicolas.sagon.authentication.composable.AuthView
import com.nicolas.sagon.authentication.event.AuthenticationEvents
import com.nicolas.sagon.authentication.state.AuthenticationState
import com.nicolas.sagon.core.theme.MailSorterTheme

@Composable
fun AuthenticationScreen(
    state: AuthenticationState,
    onUserEvent: (event: AuthenticationEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiSignInContract()) { task ->
            onUserEvent(AuthenticationEvents.OnConnectActivityResult(task = task))
        }
    AuthView(
        state = state,
        onClick = {
            authResultLauncher.launch(signInRequestCode)
        },
        modifier = modifier
    )
}

@Preview()
@Composable
fun AuthenticationScreenPreview() {
    MailSorterTheme {
        AuthenticationScreen(
            modifier = Modifier.fillMaxSize(),
            state = AuthenticationState.NotConnected,
            onUserEvent = {}
        )
    }
}