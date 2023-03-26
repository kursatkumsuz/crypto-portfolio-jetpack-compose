package com.kursatkumsuz.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kursatkumsuz.search.SearchScreen

fun NavGraphBuilder.searchScreen(navController: NavHostController) {
    composable("search_screen") {
        SearchScreen(navController = navController)
    }
}