package com.kursatkumsuz.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColumnListShimmerEffect() {
    LazyColumn {
        items(count = 5) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val infiniteTransition = rememberInfiniteTransition()
    val alphaAnimation = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    ShimmerEffectItem(alpha = alphaAnimation.value)
}

@Composable
fun ShimmerEffectItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterStart)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(20.dp)
                        .alpha(alpha),
                    shape = RoundedCornerShape(99.dp),
                    color = Color.Gray
                ) {}
                Spacer(modifier = Modifier.height(5.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .height(10.dp)
                        .alpha(alpha),
                    shape = RoundedCornerShape(99.dp),
                    color = Color.Gray
                ) {}
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .alpha(alpha)
                        .height(20.dp),
                    shape = RoundedCornerShape(99.dp),
                    color = Color.Gray
                ) {}
                Spacer(modifier = Modifier.width(5.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .alpha(alpha)
                        .height(20.dp),
                    shape = RoundedCornerShape(99.dp),
                    color = Color.Gray

                ) {}
            }
        }
    }
}



