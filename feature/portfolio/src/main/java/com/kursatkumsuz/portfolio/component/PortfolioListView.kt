package com.kursatkumsuz.portfolio.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioListView(
    portfolio: List<PortfolioModel>,
    coin: List<CoinItem>,
    onDeleteItem : (String) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(top = 20.dp, bottom = 60.dp)) {
        items(portfolio.size) { index ->


            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                val coinId = portfolio[index].symbol
                onDeleteItem(coinId)
                LaunchedEffect(key1 = Unit) {
                    dismissState.reset()
                }
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Transparent
                            else -> Color.Red
                        }, label = ""
                    )
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                        label = ""
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .background(color)
                            .clip(RoundedCornerShape(12.dp)),
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Delete Icon",
                            modifier = Modifier
                                .scale(scale)
                                .align(Alignment.CenterEnd),
                        )
                    }
                },
                dismissContent = {
                    PortfolioExpandableCard(
                        portfolio = portfolio[index],
                        coin = coin[index]
                    )
                }
            )


        }

    }
}


