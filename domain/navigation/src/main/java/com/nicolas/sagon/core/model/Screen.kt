package com.nicolas.sagon.core.model

sealed class Screen(val screenName: String) {
    data object LoginScreen : Screen("LoginScreen")
    data object HomeScreen : Screen("HomeScreen")
    data object ConfigurationScreen: Screen("ConfigurationScreen")


    companion object {
        fun getScreenFromRouteName(routeName: String?): Screen {
            return when (routeName) {
                "HomeScreen" -> HomeScreen
                "ConfigurationScreen" -> ConfigurationScreen
                else -> LoginScreen
            }
        }
    }
}
