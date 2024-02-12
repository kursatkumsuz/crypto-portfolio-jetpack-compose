package com.kursatkumsuz.market.component.circularchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import kotlinx.coroutines.launch


private fun Float.toAngle(totalValue: Float): Float {
    return this * 360 / totalValue
}

@Composable
fun CircularChart(chartData: List<PortfolioModel>, currentBalance: Double) {

    val totalBalance = chartData.sumOf { it.totalPrice.toDouble() }.toFloat()
    val btcBalance = totalBalance / 42000

    val arcAngle = remember {
        Animatable(0f)
    }

    val arcGap = remember {
        Animatable(0f)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            LaunchedEffect(key1 = Unit) {
                launch {
                    arcAngle.animateTo(360f, animationSpec = tween(1000))
                    arcGap.animateTo(2f, animationSpec = tween(1000))
                }
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .aspectRatio(1f)
            ) {
                val width = size.width
                val radius = width / 2f
                // Arc Sizes
                val arcSize = size / 1.1f
                //Stroke Widths
                val circleStrokeWidth = 50.dp.toPx()
                val arcStrokeWidth = 30.dp.toPx()

                val circumference = 2 * Math.PI * radius

                val arcStrokeAngle =
                    0.7f * (arcStrokeWidth / circumference * 360f).toFloat()
                //Angles
                var startAngle = -90f

                drawCircle(
                    brush = SolidColor(Color(0xFF333232)),
                    radius = radius / 1.1f,
                    style = Stroke(width = circleStrokeWidth)
                )

                chartData.forEachIndexed { index, data ->
                    val sweepAngle = data.totalPrice.toFloat().toAngle(totalValue = totalBalance)
                    rotate(arcAngle.value) {
                        drawArc(
                            color = colors[index % 11],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle - arcStrokeAngle * arcGap.value,
                            useCenter = false,
                            topLeft = Offset(
                                (size.width / 2) - (arcSize.width / 2),
                                (size.height / 2) - (arcSize.height / 2),
                            ),
                            size = arcSize,
                            style = Stroke(arcStrokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    startAngle += sweepAngle
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Balance", color = Color.Gray)
                Text(
                    text = String.format("%.2f", currentBalance),
                    color = Color.White,
                    fontSize = 28.sp
                )
                Text(text = "$btcBalance BTC", color = Color.Gray)
            }
        }
        ChartPortfolioList(coinList = chartData, totalBalance = totalBalance)
    }
}