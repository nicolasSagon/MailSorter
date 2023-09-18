package com.nicolas.sagon.core.viewModel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<E>: ViewModel() {
    abstract fun onUserEvent(event: E)
}