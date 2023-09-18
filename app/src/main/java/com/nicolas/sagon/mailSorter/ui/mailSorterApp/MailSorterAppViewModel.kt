package com.nicolas.sagon.mailSorter.ui.mailSorterApp

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.nicolas.sagon.core.useCase.GetInitialScreen
import com.nicolas.sagon.core.useCase.GetNavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MailSorterAppViewModel @Inject constructor(
    getNavigationController: GetNavigationController<NavHostController>,
    getInitialScreen: GetInitialScreen,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MailSorterAppState> =
        MutableStateFlow(
            MailSorterAppState(
                currentScreen = getInitialScreen(),
                navHostController = getNavigationController()
            )
        )
    val uiState: StateFlow<MailSorterAppState> = _uiState
}