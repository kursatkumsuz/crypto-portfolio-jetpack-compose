package com.kursatkumsuz.favorites.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.favorites.R
import com.kursatkumsuz.domain.model.FavoritesEntity
import com.kursatkumsuz.favorites.FavoritesViewModel
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice

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
                FavoriteListItem(
                    favoriteList[index],
                    coinList[index],
                    colorList[index % 5],
                    viewModel
                )
            }
    }
}

@Composable
fun FavoriteListItem(
    favorite: FavoritesEntity,
    coin: CoinItem,
    color: Color,
    viewModel: FavoritesViewModel,
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

                Text(
                    text = "$$price",
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(10.dp)
                )

                LastDayChangeSection(coin.quote.USD.percent_change_24h)

                IconButton(onClick = {
                    viewModel.deleteFavorite(favorite)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_star_24),
                        contentDescription = "delete icon",
                        tint = Color(0xFF22A206)
                    )
                }
            }
        }

    }
}

@Composable
fun LastDayChangeSection(lastDayChangePercentage: Double) {

    val (backgroundColor, icon) = ColorAndIcon(lastDayChangePercentage)

    Card(backgroundColor = backgroundColor) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(painter = icon, contentDescription = "chance icon", tint = Color.White)

            Text(
                text = "%${String.format("%.2f", lastDayChangePercentage)}",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun ColorAndIcon(lastDayChangePercentage: Double): Pair<Color, Painter> {
    return when {
        lastDayChangePercentage < 0 -> Pair(
            Color.Red,
            painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24)
        )
        else -> Pair(
            Color(0xFF90C70F),
            painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24)
        )
    }
}