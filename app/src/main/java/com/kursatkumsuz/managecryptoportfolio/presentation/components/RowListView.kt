package com.kursatkumsuz.managecryptoportfolio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice

@Composable
fun RowListView(coinList: List<CoinItem>) {
    LazyRow(modifier = Modifier.padding(bottom = 40.dp)) {
        items(coinList.size) { index ->
            RowListItem(coin = coinList[index])
        }
    }
}

@Composable
fun RowListItem(coin: CoinItem) {

    Card(
        modifier = Modifier
            .width(360.dp)
            .height(160.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(22.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Green
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.padding(10.dp)
                .align(Alignment.CenterStart)) {
                Text(
                    text = coin.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = coin.symbol,
                    color = Color.LightGray,
                    fontSize = 20.sp,
                )
            }
            Text(
                text = FormatCoinPrice.formatPrice(coin.quote.USD.price), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}
