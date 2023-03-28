package com.kursatkumsuz.market.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kursatkumsuz.domain.model.coin.CoinItem

@Composable
fun MarketList(navController: NavHostController, coinList : List<CoinItem>, popularCoinList : List<CoinItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            RowListView(navController = navController, coinList = popularCoinList)
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Market Data",
                fontSize = 20.sp,
                fontWeight = SemiBold,
                color = Color.White,
            )
        }
        CoinListView(coinList = coinList, navController = navController)
    }
}