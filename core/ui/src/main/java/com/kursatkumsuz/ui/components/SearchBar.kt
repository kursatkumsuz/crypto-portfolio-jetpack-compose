package com.kursatkumsuz.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    Box(modifier = Modifier.padding(top = 20.dp)) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            label = { Text(text = "Search", color = Color.LightGray)},
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            maxLines = 1,
            singleLine = true,
            modifier = modifier
                .width(350.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFF252A34),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                textColor = Color(0xFF95A6C5)
            ),
            shape = RoundedCornerShape(20.dp)
        )

    }

}