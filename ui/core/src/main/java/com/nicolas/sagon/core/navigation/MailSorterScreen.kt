package com.nicolas.sagon.core.navigation

import androidx.annotation.StringRes
import com.nicolas.sagon.core.R
import com.nicolas.sagon.core.model.Screen

@StringRes
fun Screen.getScreenNameRes(): Int {
    return when (this) {
        Screen.HomeScreen -> R.string.home_screen_title
        Screen.LoginScreen -> R.string.login_screen_title
        Screen.ConfigurationScreen -> R.string.configuration_screen_title
    }
}