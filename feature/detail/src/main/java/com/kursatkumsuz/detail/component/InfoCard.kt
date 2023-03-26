package com.kursatkumsuz.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoCard(lastChange : Double, text : String) {

    val color = if (lastChange < 0) Color.Red else Color.Green

    Card(shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier
                .size(115.dp, 65.dp)
                .background(Color(0xFF252A34))
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text, fontSize = 14.sp, color = Color.LightGray)
            Text(
                text = "%${String.format("%.2f", lastChange)}",
                fontSize = 18.sp,
                color = color
            )
        }
    }
}