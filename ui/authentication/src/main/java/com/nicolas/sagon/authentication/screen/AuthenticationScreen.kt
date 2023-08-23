package com.nicolas.sagon.authentication.screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.nicolas.sagon.authentication.activityResultContract.GoogleApiSignInContract
import com.nicolas.sagon.authentication.composable.AuthView
import com.nicolas.sagon.authentication.viewModel.AuthenticationViewModel
import com.nicolas.sagon.core.theme.MailSorterTheme

private const val TAG = "AuthenticationScreen"

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiSignInContract()) { task ->
            viewModel.onActivityResult(task)
        }
    AuthView(
        state = uiState,
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
        AuthenticationScreen(modifier = Modifier.fillMaxSize())
    }
}