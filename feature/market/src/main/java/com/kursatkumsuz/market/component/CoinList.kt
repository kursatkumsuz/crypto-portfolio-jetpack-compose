package com.kursatkumsuz.market.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice
import com.kursatkumsuz.market.R


@Composable
fun CoinListView(navController: NavHostController, coinList: List<CoinItem>) {
    LazyColumn(modifier = Modifier.padding(bottom = 40.dp)) {
        items(coinList.size) { index ->
            ListItem(coin = coinList[index], navController = navController)
        }
    }
}


@Composable
fun ListItem(navController: NavHostController, coin: CoinItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .clickable {
                navController.navigate(
                    "detail_screen/${coin.name}/${coin.quote.USD.price.toFloat()}/${coin.symbol}/${coin.quote.USD.percent_change_24h}/${coin.quote.USD.percent_change_1h}"
                )
            },
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

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = "$${formatPrice(coin.quote.USD.price)}",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(5.dp),
                )
                LastDayChangeSection(coin.quote.USD.percent_change_24h)
            }
        }
    }
}

@Composable
fun LastDayChangeSection(lastDayChangePercentage: Double) {

    val (color, icon) = colorAndIcon(
        lastDayChangePercentage
    )
        Row(
            modifier = Modifier
                .background(color)
                .size(75.dp, 30.dp)
                .clip(CircleShape),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = icon, contentDescription = "chance icon", tint = Color.White)

            Text(
                text = "%${String.format("%.2f", lastDayChangePercentage)}",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(5.dp)
            )
    }
}


@Composable
fun colorAndIcon(lastDayChangePercentage: Double): Pair<Color, Painter> {
    return when {
        lastDayChangePercentage < 0 -> Pair(
            Color(0xFFD50505),
            painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
        )
        else -> Pair(
            Color(0xFF82B804),
            painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24)
        )
    }
}
