package com.kursatkumsuz.managecryptoportfolio.presentation.screens.market.components

import androidx.compose.foundation.background
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
import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinItem
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice

@Composable
fun CoinListView(coinList: List<CoinItem>) {
    LazyColumn {
        items(coinList.size) { index ->
            ListItem(coin = coinList[index])
        }
    }
}


@Composable
fun ListItem(coin: CoinItem) {

    Card(
        modifier = Modifier
            .width(360.dp)
            .height(100.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(22.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
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
                text = formatPrice(coin), color = Color.White, fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

