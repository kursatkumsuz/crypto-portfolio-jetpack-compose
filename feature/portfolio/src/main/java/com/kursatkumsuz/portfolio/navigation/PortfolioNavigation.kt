package com.kursatkumsuz.portfolio.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kursatkumsuz.portfolio.PortfolioScreen

fun NavGraphBuilder.portfolioNavigation() {
    composable("portfolio_screen") {
        PortfolioScreen()
    }
}