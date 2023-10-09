package com.nicolas.sagon.home.viewModel

import androidx.lifecycle.viewModelScope
import com.nicolas.sagon.authentification.useCase.CheckIfUserIsConnected
import com.nicolas.sagon.authentification.useCase.GetUser
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.core.viewModel.BaseViewModel
import com.nicolas.sagon.home.event.HomeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ConfigurationViewModel"

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val navigateToScreen: NavigateToScreen,
) : BaseViewModel<HomeEvents>() {

    private val _uiState: MutableStateFlow<Unit> =
        MutableStateFlow(Unit)
    val uiState: StateFlow<Unit> = _uiState

    init {
        viewModelScope.launch {
        }
    }

    override fun onUserEvent(event: HomeEvents) {
        when (event) {
            else -> {

            }
        }
    }
}