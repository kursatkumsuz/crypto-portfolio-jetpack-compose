package com.kursatkumsuz.managecryptoportfolio.presentation.screens.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CoinListView
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.LoadingCircularProgress
import com.kursatkumsuz.managecryptoportfolio.presentation.components.market.RowListView
import kotlinx.coroutines.delay

@Composable
fun MarketScreen(navController: NavHostController) {

    val viewModel: MarketViewModel = hiltViewModel()

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                PopularCoinList(viewModel, navController)

                Text(
                    text = "Top 100",
                    fontSize = 26.sp,
                    fontWeight = SemiBold,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(10.dp))

                CoinList(viewModel, navController = navController)
            }
            LoadingProgressBar(viewModel.loadingState.value)
        }
    }
}


@Composable
fun CoinList(viewModel: MarketViewModel, navController: NavHostController) {
    val coinList = viewModel.cryptoList
    CoinListView(navController = navController, coinList = coinList.value)
}

@Composable
fun PopularCoinList(viewModel: MarketViewModel, navController: NavHostController) {
    val coinList = viewModel.popularList
    RowListView(coinList = coinList.value, navController)

}

@Composable
fun LoadingProgressBar(isLoading: Boolean) {
    if (isLoading) {
        LoadingCircularProgress()
    }
}





