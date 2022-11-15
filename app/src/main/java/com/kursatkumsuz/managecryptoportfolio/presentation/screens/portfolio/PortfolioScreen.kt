package com.kursatkumsuz.managecryptoportfolio.presentation.screens.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.presentation.components.PortfolioListView
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice

@Composable
fun PortfolioScreen(navController: NavController) {

    val viewModel: PortfolioViewModel = hiltViewModel()
    val list = viewModel.portfolioState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        CurrentBalanceCard(list)

        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Your Assets", color = Color.White, fontSize = 18.sp)

        PortfolioListView(list)

    }
}

@Composable
fun CurrentBalanceCard(list: List<PortfolioModel>) {
    val totalPrice = list.sumOf { it.price.toDouble() }
    val formattedPrice = formatPrice(totalPrice)


    Card(
        modifier = Modifier
            .size(280.dp, 140.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF393444),
                            Color(0xFF2A2633),
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Current Balance", color = Color.LightGray, fontSize = 16.sp)
            Text(text = "$${formattedPrice}", color = Color.White, fontSize = 26.sp)
        }
    }
}