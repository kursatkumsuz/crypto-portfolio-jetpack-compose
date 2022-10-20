package com.kursatkumsuz.managecryptoportfolio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.market.MarketScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.portfolio.PortfolioScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.search.SearchScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.welcome.WelcomeScreen

@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController : NavHostController) {
    NavHost(navController = navController, startDestination = "welcome_screen") {
        composable("welcome_screen") {
            WelcomeScreen(navController = navController)
        }
        composable("market_screen") {
            MarketScreen(navController = navController)
        }
        composable("search_screen") {
            SearchScreen(navController = navController)
        }
        composable("portfolio_screen") {
            PortfolioScreen(navController = navController)
        }
    }
}