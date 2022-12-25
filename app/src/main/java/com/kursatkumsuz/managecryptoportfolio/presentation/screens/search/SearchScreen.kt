package com.kursatkumsuz.managecryptoportfolio.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CoinListView
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.SearchBar

@Composable
fun SearchScreen(navController: NavHostController) {

    val viewModel: SearchViewModel = hiltViewModel()
    val searchList = viewModel.searchList
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchBar { viewModel.searchCoinList(it) }

        searchList.value?.let { CoinListView(coinList = it, navController = navController) }
    }
}