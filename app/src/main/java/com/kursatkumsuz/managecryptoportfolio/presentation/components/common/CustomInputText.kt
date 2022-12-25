package com.kursatkumsuz.managecryptoportfolio.presentation.components.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomInputText(
    labelText: String = "",
    backgroundColor: Color = MaterialTheme.colors.secondary,
    keyboardType: KeyboardType = KeyboardType.Text,
    initialText: String = "",
    onInput: (String) -> Unit,
) {

    var textState by remember { mutableStateOf(initialText) }

    TextField(
        value = textState.trim(),
        onValueChange = {
            textState = it
            onInput(it)
        },
        label = { Text(text = labelText) },
        singleLine = true,
        modifier = Modifier
            .width(320.dp)
            .height(65.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            textColor = Color(0xFF95A6C5)
        ),
        shape = RoundedCornerShape(20.dp)
    )

}