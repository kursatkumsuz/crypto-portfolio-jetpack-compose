package com.kursatkumsuz.managecryptoportfolio.components.market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.util.FormatCoinPrice

@Composable
fun RowListView(coinList: List<com.kursatkumsuz.domain.model.coin.CoinItem>, navController: NavHostController) {
    LazyRow(modifier = Modifier.padding(bottom = 40.dp)) {
        items(coinList.size) { index ->
            RowListItem(coin = coinList[index], navController)
        }
    }
}

@Composable
fun RowListItem(coin: com.kursatkumsuz.domain.model.coin.CoinItem, navController: NavHostController) {

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(220.dp)
            .padding(10.dp)
            .clickable {
                navController.navigate("detail_screen/${coin.name}/${coin.quote.USD.price.toFloat()}/${coin.symbol}/${coin.quote.USD.percent_change_24h}/${coin.quote.USD.percent_change_1h}")
            },
        shape = RoundedCornerShape(16.dp),
    ) {

        Column(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF393444),
                            Color(0xFF2A2633),
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = coin.name,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier.size(70.dp, 25.dp),
                shape = RoundedCornerShape(99.dp)
            ) {
                Row(
                    modifier = Modifier.background(Color.White)
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = coin.symbol,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    )
                }
            }
            Text(
                text = "$${com.kursatkumsuz.util.FormatCoinPrice.formatPrice(coin.quote.USD.price)}",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}
