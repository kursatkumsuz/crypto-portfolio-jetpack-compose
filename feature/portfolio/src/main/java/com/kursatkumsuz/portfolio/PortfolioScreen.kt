package com.kursatkumsuz.portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kursatkumsuz.managecryptoportfolio.components.portfolio.PieChart
import com.kursatkumsuz.portfolio.component.PortfolioListView
import com.kursatkumsuz.ui.components.common.LoadingCircularProgress
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice
import kotlinx.coroutines.delay

@Composable
fun PortfolioScreen() {

    val viewModel: PortfolioViewModel = hiltViewModel()
    val list = viewModel.selectedPortfolioList.value
    val chartList = viewModel.chartList.value
    val coinList = viewModel.selectedCoinList.value
    val isLoading = viewModel.loadingState.value
    val lastBalance = viewModel.totalCurrentBalanceState.value
    val buyingPrice = viewModel.totalBuyingPriceState.value
    val profit = viewModel.profitState.value

    var refreshState by remember { mutableStateOf(false) }

    LaunchedEffect(refreshState) {
        if (refreshState) {
            viewModel.refresh()
            delay(100)
            refreshState = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshState),
        onRefresh = { refreshState = true }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary),
            ) {


                Spacer(modifier = Modifier.height(20.dp))
                CustomTableRow(lastBalance, buyingPrice, profit, chartList)
                Spacer(modifier = Modifier.height(40.dp))

                Text(text = "Your Assets", color = Color.White, fontSize = 18.sp)

                PortfolioListView(list, coinList, viewModel)
            }
            if (isLoading)
                LoadingCircularProgress()
        }

    }

}

@Composable
fun CurrentBalanceCard(
    lastBalance: Double,
    buyingPrice: Double,
    profit: Double,
) {
    val formattedLastBalance = formatPrice(lastBalance)
    val formattedBuyingPrice = formatPrice(buyingPrice)

    Card(
        modifier = Modifier
            .size(340.dp, 180.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF393444),
                            Color(0xFF2A2633),
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Current Balance", color = Color.LightGray, fontSize = 16.sp)
            Text(text = "$$formattedLastBalance", color = Color.White, fontSize = 26.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Text(text = "Total Buying Price", color = Color.LightGray, fontSize = 16.sp)
                    Text(text = "$$formattedBuyingPrice", color = Color.White, fontSize = 14.sp)
                }

                Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Text(text = "Profit/Loss", color = Color.LightGray, fontSize = 16.sp)
                    Text(
                        text = "%${String.format("%.2f", profit)}",
                        color = if (profit > 0) Color.Green else Color.Red,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun CustomTableRow(
    lastBalance: Double,
    buyingPrice: Double,
    profit: Double,
    chartList: List<com.kursatkumsuz.domain.model.portfolio.PieChartModel>
) {
    var selectedIndex by remember { mutableStateOf(0) }
    var visible by remember { mutableStateOf(true) }
    val list = listOf("Balance", "Chart")
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = Color(0xFF223449),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(50))
                .padding(1.dp),
            indicator = { tabPositions: List<TabPosition> -> }
        ) {
            list.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                Tab(
                    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.White
                        )
                    else Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color(
                                0xFF223449
                            )
                        ),
                    selected = selected,
                    onClick = {
                        selectedIndex = index
                        visible = selectedIndex == 0
                    },
                    text = { Text(text = text, color = Color(0xFF7190B4)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        AnimatedVisibility(visible = visible) {
            CurrentBalanceCard(
                lastBalance,
                buyingPrice,
                profit
            )
        }

        AnimatedVisibility(visible = !visible) {
            PieChart(dataList = chartList)
        }
    }
}
