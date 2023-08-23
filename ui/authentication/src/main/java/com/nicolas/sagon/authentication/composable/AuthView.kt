package com.nicolas.sagon.authentication.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicolas.sagon.authentication.state.AuthenticationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthView(
    state: AuthenticationState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold {
        Box(modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (state) {
                    is AuthenticationState.UserConnected -> {
                        Text("User : ${state.user.email} with token : ${state.user.idToken} is connected")
                    }

                    is AuthenticationState.Error -> {
                        Text("Error while connecting the user")
                        SignInGoogleButton(onclick = onClick)
                    }

                    AuthenticationState.Loading -> FullScreenLoaderComponent()
                    AuthenticationState.NotConnected -> {
                        SignInGoogleButton(onclick = onClick)
                    }
                }

            }
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PreviewAuthView() {
    AuthView(state = AuthenticationState.NotConnected, onClick = {})
}