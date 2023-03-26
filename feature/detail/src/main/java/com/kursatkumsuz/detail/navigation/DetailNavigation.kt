package com.kursatkumsuz.detail.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kursatkumsuz.detail.DetailScreen

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.detailScreen(navController : NavHostController) {
    composable(
        "detail_screen/{name}/{price}/{symbol}/{lastDayChange}/{lastOneHourChange}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("price") { type = NavType.FloatType },
            navArgument("symbol") { type = NavType.StringType },
            navArgument("lastDayChange") { type = NavType.FloatType },
            navArgument("lastOneHourChange") { type = NavType.FloatType },

            )
    ) {
        val name = remember { it.arguments?.getString("name") }
        val price = remember { it.arguments?.getFloat("price") }
        val symbol = remember { it.arguments?.getString("symbol") }
        val lastDayChange = remember { it.arguments?.getFloat("lastDayChange") }
        val lastOneHourChange = remember { it.arguments?.getFloat("lastOneHourChange") }

        DetailScreen(
            navController,
            name ?: "",
            price ?: 0f,
            symbol ?: "",
            lastDayChange ?: 0f,
            lastOneHourChange ?: 0f
        )
    }
}