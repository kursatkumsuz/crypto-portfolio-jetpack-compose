package com.kursatkumsuz.portfolio.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice

@Composable
fun PortfolioExpandableCard(portfolio: PortfolioModel, coin: CoinItem) {
    var isExpandState by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(5.dp)
            .clickable { isExpandState = !isExpandState },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colors.secondary)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = portfolio.name,
                            color = Color.White,
                            fontSize = 18.sp,
                        )

                        Text(
                            text = "(${portfolio.symbol})",
                            color = Color.LightGray,
                            fontSize = 14.sp,
                        )
                    }

                    Text(
                        text = "Amount",
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )

                    Text(
                        text = portfolio.amount,
                        color = Color.LightGray,
                        fontSize = 13.sp,
                    )
                }
                Text(
                    text = "$${formatPrice(portfolio.totalPrice.toDouble())}",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterEnd)
                )
            }
            AnimatedVisibility(
                visible = isExpandState,
                enter = fadeIn() + expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                SectionOfExpanded(portfolio = portfolio, coin = coin)

            }

        }
    }
}

@Composable
fun SectionOfExpanded(portfolio: PortfolioModel, coin: CoinItem) {
    val lastPrice = formatPrice(coin.quote.USD.price)
    val buyingPrice = formatPrice(portfolio.buyingPrice.toDouble())
    val profit =
        (coin.quote.USD.price - portfolio.buyingPrice.toDouble()) * 100 / portfolio.buyingPrice.toDouble()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
    ) {

        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text(text = "Buying Price", fontSize = 13.sp, color = Color.DarkGray)
            Text(text = buyingPrice, fontSize = 14.sp, color = Color.LightGray)
        }

        Column(modifier = Modifier.align(Alignment.TopEnd)) {
            Text(text = "Last Price", fontSize = 13.sp, color = Color.DarkGray)
            Text(text = lastPrice, fontSize = 14.sp, color = Color.LightGray)
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Profit/Loss", fontSize = 14.sp, color = Color.LightGray)
            Text(
                text = "%${String.format("%.2f", profit)}",
                fontSize = 14.sp,
                color = if (profit >= 0) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        }
    }
}