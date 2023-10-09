package com.nicolas.sagon.home.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.core.viewModel.BaseViewModel
import com.nicolas.sagon.home.event.HomeEvents
import com.nicolas.sagon.mail.model.formattedString
import com.nicolas.sagon.mail.useCase.GetUserMailThreads
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AuthenticationViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigateToScreen: NavigateToScreen,
    private val getUserMailThreads: GetUserMailThreads,
) : BaseViewModel<HomeEvents>() {

    init {
        viewModelScope.launch {
            getUserMailThreads().collectLatest {
                Log.d(TAG, "Threads = ${it.formattedString()}")
            }
        }
    }

    override fun onUserEvent(event: HomeEvents) {
        when (event) {
            else -> {}
        }
    }
}