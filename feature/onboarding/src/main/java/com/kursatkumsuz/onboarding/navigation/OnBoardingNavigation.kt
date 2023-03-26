package com.kursatkumsuz.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kursatkumsuz.onboarding.WelcomeScreen

fun NavGraphBuilder.onBoardingScreen(navController : NavHostController) {
    composable("welcome_screen") {
        WelcomeScreen(navController = navController)
    }
}