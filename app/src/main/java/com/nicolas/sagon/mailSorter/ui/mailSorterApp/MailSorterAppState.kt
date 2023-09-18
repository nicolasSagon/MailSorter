package com.nicolas.sagon.mailSorter.ui.mailSorterApp

import androidx.navigation.NavHostController
import com.nicolas.sagon.core.model.Screen

data class MailSorterAppState(
    val initScreen: Screen,
    val currentScreen: Screen,
    val navHostController: NavHostController
)