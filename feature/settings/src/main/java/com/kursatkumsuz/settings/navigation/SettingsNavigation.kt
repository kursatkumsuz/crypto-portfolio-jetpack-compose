package com.kursatkumsuz.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kursatkumsuz.settings.SettingsScreen

fun NavGraphBuilder.settingsScreen(navController: NavHostController) {
    composable("settings_screen") {
        SettingsScreen(navController = navController)
    }
}