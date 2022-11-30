package com.kursatkumsuz.managecryptoportfolio.presentation.components.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.managecryptoportfolio.R
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.presentation.screens.favorites.FavoritesViewModel
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice

@ExperimentalMaterialApi
@Composable
fun FavoriteListView(
    coinList: List<CoinItem>,
    favoriteList: List<FavoritesEntity>,
    viewModel: FavoritesViewModel
) {

    val colorList: List<Color> = listOf(
        Color(0xFF2F0B3C),
        Color(0xFF09253A),
        Color(0xFF012D36),
        Color(0xFF01262E),
        Color(0xFF241949)
    )

    LazyColumn {
        if (favoriteList.isNotEmpty() && coinList.isNotEmpty())
            items(favoriteList.size) { index ->
                GridListItem(favoriteList[index], coinList[index], colorList[index % 5], viewModel)
            }
    }
}

@Composable
fun GridListItem(
    favorite: FavoritesEntity,
    coin: CoinItem,
    color: Color,
    viewModel: FavoritesViewModel
) {

    val price = formatPrice(coin.quote.USD.price)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = color
    ) {
        Box(modifier = Modifier.padding(10.dp)) {

            Column(modifier = Modifier.align(Alignment.CenterStart)) {

                Text(text = coin.name, fontSize = 18.sp, color = Color.White)

                Text(text = coin.symbol, fontSize = 14.sp, color = Color.LightGray)
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "$$price", fontSize = 16.sp, color = Color.LightGray)

                IconButton(onClick = {
                    viewModel.deleteFavorite(favorite)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "delete icon",
                    )
                }
            }
        }

    }
}