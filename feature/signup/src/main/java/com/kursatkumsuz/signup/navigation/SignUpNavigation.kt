package com.kursatkumsuz.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kursatkumsuz.signup.SignUpScreen

fun NavGraphBuilder.signUpScreen(navController : NavHostController) {
    composable("signup_screen") {
        SignUpScreen(navController = navController)
    }
}