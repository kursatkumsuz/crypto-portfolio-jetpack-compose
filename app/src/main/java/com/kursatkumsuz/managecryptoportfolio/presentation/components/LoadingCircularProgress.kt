package com.kursatkumsuz.managecryptoportfolio.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingCircularProgress() {
    CircularProgressIndicator(modifier = Modifier.size(120.dp), color = Color.Blue, strokeWidth = 2.dp)

}