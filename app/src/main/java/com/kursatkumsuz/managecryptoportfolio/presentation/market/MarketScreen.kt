package com.kursatkumsuz.managecryptoportfolio.presentation.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kursatkumsuz.managecryptoportfolio.presentation.market.components.CoinListView

@Composable
fun MarketScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
    ) {
        Text(
            text = "Top 100",
            fontSize = 26.sp,
            fontWeight = SemiBold,
            color = Color.White,
            modifier = Modifier.padding(10.dp)
        )

        CoinList()

    }
}

@Composable
fun CoinList(viewModel: MarketViewModel = hiltViewModel()) {
    val coinList = viewModel.coinLiveData.observeAsState()
    coinList.value?.let { CoinListView(coinList = it) }
}




