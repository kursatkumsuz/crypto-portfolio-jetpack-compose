package com.kursatkumsuz.managecryptoportfolio.presentation.screens.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.presentation.components.CoinListView
import com.kursatkumsuz.managecryptoportfolio.presentation.components.RowListView

@Composable
fun MarketScreen(navController: NavHostController) {

    val viewModel: MarketViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PopularCoinList(viewModel)

        Text(
            text = "Top 100",
            fontSize = 26.sp,
            fontWeight = SemiBold,
            color = Color.White,
            modifier = Modifier.padding(10.dp)
        )

      CoinList(viewModel, navController = navController)
    }
}



@Composable
fun CoinList(viewModel: MarketViewModel, navController: NavHostController) {
    val coinList = viewModel.cryptoList
    CoinListView(navController = navController, coinList = coinList.value)
}

@Composable
fun PopularCoinList(viewModel: MarketViewModel) {
    val coinList = viewModel.cryptoList
    RowListView(coinList = coinList.value.take(2))

}





