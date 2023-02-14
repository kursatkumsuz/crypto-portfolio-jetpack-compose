package com.kursatkumsuz.managecryptoportfolio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.BottomNavigationBar
import com.kursatkumsuz.managecryptoportfolio.presentation.navigation.SetupNavGraph
import com.kursatkumsuz.managecryptoportfolio.ui.theme.ManageCryptoPortfolioTheme
import com.kursatkumsuz.managecryptoportfolio.util.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageCryptoPortfolioTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        if (currentRoute(navController) != "welcome_screen" &&
                            currentRoute(navController) != "signin_screen" &&
                            currentRoute(navController) != "signup_screen"
                        ) {

                            BottomNavigationBar(items = listOf(

                                BottomNavItem("Market", "market_screen", Icons.Default.Home),
                                BottomNavItem("Search", "search_screen", Icons.Default.Search),
                                BottomNavItem(
                                    "Favorite",
                                    "favorites_screen",
                                    Icons.Default.Favorite
                                ),
                                BottomNavItem(
                                    "Portfolio",
                                    "portfolio_screen",
                                    Icons.Default.List
                                ),
                                BottomNavItem(
                                    "Settings",
                                    "settings_screen",
                                    Icons.Default.Settings
                                )

                            ), navController = navController, onItemClick = {
                                navController.popBackStack()
                                navController.navigate(it.route)
                            })
                        }
                    }
                ) {
                    SetupNavGraph(navController)
                }
            }
        }
    }


    @Composable
    private fun currentRoute(navController: NavController): String? {
        val route = navController.currentBackStackEntryAsState().value?.destination?.route
        return route
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ManageCryptoPortfolioTheme {
    }
}