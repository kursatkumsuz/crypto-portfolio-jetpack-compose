package com.kursatkumsuz.managecryptoportfolio.presentation.components

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
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice

@Composable
fun PortfolioListView(portfolio: List<PortfolioModel>) {
    LazyColumn(contentPadding = PaddingValues(top = 20.dp, bottom = 60.dp)) {
        items(portfolio.size) { index ->
            PortfolioListItem(portfolio[index])
        }

    }
}

@Composable
fun PortfolioListItem(portfolio: PortfolioModel) {

    Card(
        modifier = Modifier
            .size(360.dp, 110.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterStart)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = portfolio.name,
                        color = Color.White,
                        fontSize = 18.sp,
                    )

                    Text(
                        text = " (${portfolio.symbol})",
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
                text = "$${FormatCoinPrice.formatPrice(portfolio.price.toDouble())}",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}