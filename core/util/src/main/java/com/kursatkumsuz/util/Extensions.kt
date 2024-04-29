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

