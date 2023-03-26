package com.kursatkumsuz.portfolio.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.kursatkumsuz.portfolio.PortfolioViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PortfolioListView(
    portfolio: List<PortfolioModel>,
    coin: List<CoinItem>,
    viewModel: PortfolioViewModel
) {
    LazyColumn(contentPadding = PaddingValues(top = 20.dp, bottom = 60.dp)) {
        items(portfolio.size) { index ->

            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                LaunchedEffect(key1 = Unit) {

                    portfolio[index].id?.let {
                        viewModel.deletePortfolio(it)
                        dismissState.reset()
                    }
                }
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                dismissThresholds = { direction ->
                    FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                },
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> MaterialTheme.colors.secondary
                            else -> Color.Red
                        }
                    )
                    val icon = Icons.Default.Delete

                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
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
                    if (portfolio.isNotEmpty() && coin.isNotEmpty()) {
                        PortfolioExpandableCard(portfolio[index], coin[index])
                    }
                }

            )

        }

    }
}


