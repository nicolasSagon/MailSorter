package com.nicolas.sagon.mailSorter.ui.mailSorterApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.useCase.GetInitialScreen
import com.nicolas.sagon.core.useCase.GetNavigationController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MailSorterAppViewModel @Inject constructor(
    getNavigationController: GetNavigationController<NavHostController>,
    getInitialScreen: GetInitialScreen,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MailSorterAppState> =
        MutableStateFlow(
            MailSorterAppState(
                initScreen = getInitialScreen(),
                currentScreen = getInitialScreen(),
                navHostController = getNavigationController()
            )
        )
    val uiState: StateFlow<MailSorterAppState> = _uiState

    init {
        viewModelScope.launch {
            uiState.value.navHostController.currentBackStackEntryFlow.collect {
                _uiState.value =
                    _uiState.value.copy(currentScreen = Screen.getScreenFromRouteName(it.destination.route))
            }
        }
    }
}