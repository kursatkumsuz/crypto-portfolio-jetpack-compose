package com.kursatkumsuz.managecryptoportfolio.presentation.components.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.R

@Composable
fun BackButton(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {

        IconButton(
            onClick = { navController.popBackStack() },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                tint = Color.White,
                contentDescription = "back icon"
            )
        }
    }
}