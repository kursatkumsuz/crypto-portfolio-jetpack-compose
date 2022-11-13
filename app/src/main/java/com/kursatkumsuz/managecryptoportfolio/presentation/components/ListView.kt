package com.kursatkumsuz.managecryptoportfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice

@Composable
fun CoinListView(navController: NavHostController,coinList: List<CoinItem>) {
    LazyColumn(modifier = Modifier.padding(bottom = 40.dp)) {
        items(coinList.size) { index ->
            ListItem(coin = coinList[index], navController = navController)
        }
    }
}


@Composable
fun ListItem(navController: NavHostController,coin: CoinItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .clickable { navController.navigate("detail_screen/${coin.name}/${coin.quote.USD.price.toFloat()}/${coin.symbol}") },
        shape = RoundedCornerShape(22.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = coin.name,
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Text(
                    text = coin.symbol,
                    color = Color.LightGray,
                    fontSize = 15.sp,
                )
            }
            Text(
                text = formatPrice(coin.quote.USD.price), color = Color.White, fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

