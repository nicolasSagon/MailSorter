package com.nicolas.sagon.mailSorter.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.nicolas.sagon.core.service.NavigationService
import com.nicolas.sagon.core.useCase.GetInitialScreen
import com.nicolas.sagon.core.useCase.GetNavigationController
import com.nicolas.sagon.core.useCase.NavigateToScreen
import com.nicolas.sagon.mailSorter.navigation.AndroidNavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NavigationModule {

    @Provides
    @Singleton
    fun provideNavController(@ApplicationContext context: Context) =
        NavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
        }

    @Provides
    @Singleton
    fun provideNavigationService(
        navHostController: NavHostController,
    ): NavigationService<NavHostController> {
        return AndroidNavigationService(navHostController)
    }

    // Navigation useCases

    @Provides
    fun provideGetNavigationControllerUseCase(
        navigationService: NavigationService<NavHostController>,
    ): GetNavigationController<NavHostController> {
        return GetNavigationController(navigationService)
    }

    @Provides
    fun provideGetInitialScreen(): GetInitialScreen {
        return GetInitialScreen()
    }

    @Provides
    fun provideNavigateToScreen(
        navigationService: NavigationService<NavHostController>,
    ): NavigateToScreen {
        return NavigateToScreen(navigationService)
    }
}