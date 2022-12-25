package com.kursatkumsuz.managecryptoportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kursatkumsuz.managecryptoportfolio.presentation.navigation.SetupNavGraph
import com.kursatkumsuz.managecryptoportfolio.ui.theme.ManageCryptoPortfolioTheme
import com.kursatkumsuz.managecryptoportfolio.util.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
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
                                BottomNavItem("Favorite", "favorites_screen", Icons.Default.Favorite),
                                BottomNavItem("Portfolio", "portfolio_screen", Icons.Default.List),
                                BottomNavItem("Settings", "settings_screen", Icons.Default.Settings)

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

    @ExperimentalAnimationApi
    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()

        println("isVisible ${backStackEntry?.destination?.route}")

        BottomNavigation(
            modifier = modifier,
            backgroundColor = Color.Black,
            elevation = 10.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    selectedContentColor = Color.Gray,
                    unselectedContentColor = Color.DarkGray,
                    onClick = { onItemClick(item) },
                    icon = {
                        AnimatedVisibility(visible = selected,) {
                            Row(
                                modifier = modifier
                                    .background(MaterialTheme.colors.primary),
                                verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(modifier = Modifier.clip(CircleShape)
                                        .background(Color(0x9A293646))) {
                                        Icon(imageVector = item.icon, contentDescription = item.name, modifier = Modifier.padding(10.dp))
                                    }
                            }
                        }
                        AnimatedVisibility(
                            visible = !selected) {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                    })
            }
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    return route
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ManageCryptoPortfolioTheme {
    }
}