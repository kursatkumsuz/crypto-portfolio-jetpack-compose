package com.kursatkumsuz.managecryptoportfolio.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Toast(context : Context, message : String) {
   Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}