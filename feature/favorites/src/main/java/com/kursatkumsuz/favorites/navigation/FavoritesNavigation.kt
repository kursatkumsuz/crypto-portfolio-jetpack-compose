package com.kursatkumsuz.favorites.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kursatkumsuz.favorites.FavoritesScreen

fun NavGraphBuilder.favoritesNavigation() {
    composable(route = "favorites_screen") {
        FavoritesScreen()
    }
}