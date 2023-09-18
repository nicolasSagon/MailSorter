package com.nicolas.sagon.mailSorter.ui.mailSorterApp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolas.sagon.authentication.screen.AuthenticationScreen
import com.nicolas.sagon.authentication.viewModel.AuthenticationViewModel
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.navigation.getScreenNameRes
import com.nicolas.sagon.mailSorter.ui.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailSorterApp() {
    val navigationViewModel = hiltViewModel<MailSorterAppViewModel>()
    val navigationState = navigationViewModel.uiState.collectAsState()

    val navHostController = navigationState.value.navHostController

    Scaffold(
        topBar = {
            AppTopBar(
                currentScreenName = stringResource(id = navigationState.value.currentScreen.getScreenNameRes()),
                canNavigateBack = navHostController.previousBackStackEntry != null,
                navigateUp = { navHostController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navHostController,
            startDestination = Screen.LoginScreen.screenName,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.LoginScreen.screenName) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                AuthenticationScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}