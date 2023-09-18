package com.nicolas.sagon.core.service

import com.nicolas.sagon.core.model.Screen

interface NavigationService<T> {
    fun navigateToScreen(targetScreen: Screen)
    fun getNavigationController(): T
}