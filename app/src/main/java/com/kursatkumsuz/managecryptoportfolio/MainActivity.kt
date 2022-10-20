package com.kursatkumsuz.managecryptoportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kursatkumsuz.managecryptoportfolio.presentation.market.MarketScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.portfolio.PortfolioScreen
import com.kursatkumsuz.managecryptoportfolio.presentation.search.SearchScreen
import com.kursatkumsuz.managecryptoportfolio.ui.theme.ManageCryptoPortfolioTheme
import com.kursatkumsuz.managecryptoportfolio.util.BottomNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageCryptoPortfolioTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(items = listOf(
                            BottomNavItem("Market", "market_screen", Icons.Default.Home),
                            BottomNavItem("Search", "search_screen", Icons.Default.Search),
                            BottomNavItem("Portfolio", "portfolio_screen", Icons.Default.Add)
                        ), navController = navController, onItemClick = {
                            navController.navigate(it.route)
                        })
                    }
                ) {
                    Navigation(navController)
                }
                BottomSheetScaffold(sheetContent = {

                }) {

                }
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "market_screen") {
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

    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            modifier = modifier,
            backgroundColor = Color.Black,
            elevation = 10.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    selectedContentColor = Color.Gray,
                    unselectedContentColor = Color.DarkGray,
                    onClick = { onItemClick(item) },
                    icon = {
                        AnimatedVisibility(selected) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = modifier
                                    .background(MaterialTheme.colors.primary)
                                    .padding(10.dp)
                                    .clip(shape = RoundedCornerShape(99.dp)),

                                ) {
                                Icon(imageVector = item.icon, contentDescription = item.name)
                                Text(
                                    text = item.name,
                                    fontSize = 13.sp,
                                    modifier = modifier.padding(3.dp)
                                )
                            }
                        }
                        AnimatedVisibility (!selected) {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                    })

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ManageCryptoPortfolioTheme {
    }
}