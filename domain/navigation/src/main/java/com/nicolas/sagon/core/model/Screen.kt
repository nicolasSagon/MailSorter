package com.nicolas.sagon.core.model

sealed class Screen(val screenName: String) {
    object LoginScreen : Screen("LoginScreen")
    object HomeScreen : Screen("HomeScreen")


    companion object {
        fun getScreenFromRouteName(routeName: String?): Screen {
            return when (routeName) {
                "HomeScreen" -> HomeScreen
                else -> LoginScreen
            }
        }
    }
}
