package com.kursatkumsuz.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import java.util.Locale

@Composable
fun Float.toAnimate(durationMillis : Int = 2000, delay : Long = 0): Float {
    val resultValue = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = Unit) {
        delay(delay)
        resultValue.animateTo(this@toAnimate, animationSpec = tween(durationMillis))
    }

    return resultValue.value
}

fun Double.toFormatPrice(): String {
    val dec = DecimalFormat("#,###.00")
    val formattedPrice = String.format(Locale.US, "%.10f", this)
    val zeroCount = formattedPrice.drop(2)
        .takeWhile { it == '0' }.length
    return if (zeroCount >= 5) {
        val remainingDigits = formattedPrice.drop(2 + zeroCount)
        "0.{${zeroCount}}${remainingDigits}"
    } else {
        dec.format(this)
    }
}