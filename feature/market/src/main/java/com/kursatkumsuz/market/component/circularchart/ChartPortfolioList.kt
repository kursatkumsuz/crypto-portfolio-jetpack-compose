package com.kursatkumsuz.market.component.circularchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel

private fun Float.toPercentage(totalValue: Float): Float {
    return this / totalValue * 100
}

@Composable
fun ChartPortfolioList(coinList: List<PortfolioModel>, totalBalance: Float) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(coinList.size) { index ->
            ListItem(
                coin = coinList[index],
                totalBalance = totalBalance,
                color = colors[index % 11]
            )
        }
    }
}

@Composable
fun ListItem(coin: PortfolioModel, totalBalance: Float, color: Color) {

    val progressValue = coin.totalPrice.toFloat().toPercentage(totalValue = totalBalance)

    val progress = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = progress) {
        progress.animateTo(targetValue = progressValue, animationSpec = tween(2000))
    }

    Box(
        modifier = Modifier
            .padding(top = 30.dp, start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .size(width = 130.dp, height = 80.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = coin.symbol, color = Color(0xFF868EB8))
            LinearProgressIndicator(
                progress = progress.value / 100f,
                modifier = Modifier.padding(horizontal = 10.dp),
                color = color,
                trackColor = Color(0xFF434C64),
            )
            Text(
                text = "${"%.2f".format(progress.value)} %", color = Color.White
            )

        }
    }
}