package com.kursatkumsuz.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kursatkumsuz.favorites.component.FavoriteListView
import com.kursatkumsuz.ui.components.ColumnListShimmerEffect

@Composable
fun FavoritesScreen() {

    val viewModel: FavoritesViewModel = hiltViewModel()
    val coinList = viewModel.coinList.value
    val favoriteList = viewModel.favoriteList.value
    val loadingState = viewModel.loadingState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {

        if(loadingState) {
            ColumnListShimmerEffect()
        }else {
            FavoriteListView(coinList, favoriteList, viewModel)
        }
    }
}
