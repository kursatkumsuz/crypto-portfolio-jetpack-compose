package com.kursatkumsuz.portfolio.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice
import com.kursatkumsuz.util.chartColorList
import kotlin.math.PI
import kotlin.math.atan2


@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    radius: Float = 350f,
    innerRadius: Float = 175f,
    dataList: List<com.kursatkumsuz.domain.model.portfolio.PieChartModel>,
) {

    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var data by remember { mutableStateOf(dataList) }

    var isCenterClicked by remember { mutableStateOf(false) }

    val colorList = chartColorList()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = modifier
            .size(180.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = { offset ->
                        val tapAngleInDegrees = (
                                -atan2(
                                    x = circleCenter.y - offset.y,
                                    y = circleCenter.x - offset.x
                                ) * (180f / PI).toFloat() - 90f).mod(360f)

                        val centerClicked = if (tapAngleInDegrees < 90) {
                            offset.x < circleCenter.x + innerRadius && offset.y < circleCenter.y + innerRadius
                        } else if (tapAngleInDegrees < 180) {
                            offset.x > circleCenter.x - innerRadius && offset.y < circleCenter.y + innerRadius
                        } else if (tapAngleInDegrees < 270) {
                            offset.x > circleCenter.x - innerRadius && offset.y > circleCenter.y - innerRadius
                        } else {
                            offset.x < circleCenter.x + innerRadius && offset.y > circleCenter.y - innerRadius
                        }

                        if (centerClicked) {
                            data = data.map {
                                it.copy(isClicked = !isCenterClicked)
                            }
                            isCenterClicked = !isCenterClicked
                        } else {
                            val anglePerValue = 360f / dataList.sumOf {
                                it.price
                            }
                            var currAngle = 0f
                            data.forEach { element ->
                                currAngle += element.price.toFloat() * anglePerValue.toFloat()
                                if (tapAngleInDegrees < currAngle) {
                                    val description = element.symbol
                                    data = data.map {
                                        if (description == it.symbol) {
                                            it.copy(isClicked = !it.isClicked)
                                        } else {
                                            it.copy(isClicked = false)
                                        }
                                    }
                                    return@detectTapGestures
                                }
                            }
                        }
                    }
                )
            }
        ) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val totalValue = dataList.sumOf { it.price }

            val anglePerValue = 360f / totalValue
            var currentStartAngle = 0f

            data.forEachIndexed { index, element ->
                val scale = if (element.isClicked) 1.1f else 1.0f
                val angleToDraw = element.price * anglePerValue
                scale(scale) {
                    drawArc(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                colorList[index % 11],
                            )
                        ),
                        startAngle = currentStartAngle,
                        sweepAngle = angleToDraw.toFloat(),
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width - radius * 2f) / 2f,
                            (height - radius * 2f) / 2f
                        )
                    )
                    currentStartAngle += angleToDraw.toFloat()
                }

                var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                var factor = 1f

                if (rotateAngle > 90f) {
                    rotateAngle = (rotateAngle + 180).mod(360f)
                    factor = -0.92f
                }
                val percentage = (element.price / totalValue.toFloat() * 100).toFloat()

                drawContext.canvas.nativeCanvas.apply {

                    if (percentage > 3) {
                        rotate(rotateAngle.toFloat()) {
                            drawText(
                                "%${String.format("%.2f", percentage)}",
                                circleCenter.x,
                                circleCenter.y + (radius - (radius - innerRadius) / 2f) * factor,
                                Paint().apply {
                                    textSize = 13.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }

                if (element.isClicked) {
                    rotate(rotateAngle.toFloat()) {
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "${element.symbol} : ${formatPrice(element.price)}",
                                circleCenter.x,
                                circleCenter.y + radius * 1.3f * factor,
                                Paint().apply {
                                    textSize = 14.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}