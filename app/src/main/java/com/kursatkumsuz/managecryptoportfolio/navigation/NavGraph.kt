package com.kursatkumsuz.managecryptoportfolio.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kursatkumsuz.favorites.navigation.favoritesNavigation
import com.kursatkumsuz.detail.navigation.detailScreen
import com.kursatkumsuz.settings.SettingsScreen
import com.kursatkumsuz.market.navigation.marketScreen
import com.kursatkumsuz.onboarding.navigation.onBoardingScreen
import com.kursatkumsuz.portfolio.navigation.portfolioNavigation
import com.kursatkumsuz.search.navigation.searchScreen
import com.kursatkumsuz.settings.navigation.settingsScreen
import com.kursatkumsuz.signin.navigation.signInNavigation
import com.kursatkumsuz.signup.navigation.signUpScreen

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome_screen") {

        onBoardingScreen(navController = navController)
        favoritesNavigation()
        signInNavigation(navController = navController)
        signUpScreen(navController = navController)
        marketScreen(navController = navController)
        searchScreen(navController = navController)
        detailScreen(navController = navController)
        settingsScreen(navController = navController)
        portfolioNavigation()
    }
}