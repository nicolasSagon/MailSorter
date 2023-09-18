package com.nicolas.sagon.mailSorter.navigation

import androidx.navigation.NavHostController
import com.nicolas.sagon.core.model.Screen
import com.nicolas.sagon.core.service.NavigationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AndroidNavigationService @Inject constructor(private val navHostController: NavHostController) :
    NavigationService<NavHostController> {

    override fun navigateToScreen(targetScreen: Screen) {
        navHostController.navigate(targetScreen.screenName)
    }

    override fun getNavigationController() = navHostController

}