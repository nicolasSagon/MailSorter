package com.nicolas.sagon.core.useCase

import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.service.NavigationService

class NavigateToScreen(
    private val navigationService: NavigationService<*>,
) {
    operator fun invoke(targetScreen: Screen) {
        navigationService.navigateToScreen(targetScreen)
    }
}