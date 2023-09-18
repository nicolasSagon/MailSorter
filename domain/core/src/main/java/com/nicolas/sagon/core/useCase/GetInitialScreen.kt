package com.nicolas.sagon.core.useCase

import com.nicolas.sagon.core.model.Screen

class GetInitialScreen() {
    operator fun invoke(): Screen {
        return Screen.LoginScreen
    }
}