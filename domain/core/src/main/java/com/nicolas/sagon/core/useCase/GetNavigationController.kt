package com.nicolas.sagon.core.useCase

import com.nicolas.sagon.core.service.NavigationService

class GetNavigationController<T>(
    private val navigationService: NavigationService<T>,
) {
    operator fun invoke(): T {
        return navigationService.getNavigationController()
    }
}