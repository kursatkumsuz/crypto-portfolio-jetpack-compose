package com.kursatkumsuz.managecryptoportfolio.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.detail.DetailScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.favorites.FavoritesScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.market.MarketScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.portfolio.PortfolioScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.search.SearchScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.signin.SignInScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.signup.SignUpScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.welcome.WelcomeScreen

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome_screen") {
        composable("welcome_screen") {
            WelcomeScreen(navController = navController)
        }
        composable("signin_screen") {
            SignInScreen(navController)
        }
        composable("signup_screen") {
            SignUpScreen(navController)
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
        composable("favorites_screen") {
            FavoritesScreen()
        }
        composable("detail_screen/{name}/{price}/{symbol}", arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("price") { type = NavType.FloatType },
            navArgument("symbol") { type = NavType.StringType }

        )) {
            val name = remember { it.arguments?.getString("name") }
            val price = remember { it.arguments?.getFloat("price") }
            val symbol = remember { it.arguments?.getString("symbol") }
            DetailScreen(name ?: "", price ?: 0f, symbol ?: "")
        }
    }
}