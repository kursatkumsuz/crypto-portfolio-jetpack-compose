package com.kursatkumsuz.managecryptoportfolio.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kursatkumsuz.managecryptoportfolio.R

@Composable
fun CustomPasswordText(
    onInput: (String) -> Unit,

    ) {

    var passwordState by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }


    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.ic_visible)
    } else {
        painterResource(id = R.drawable.ic_invisible)
    }

    TextField(
        value = passwordState,
        onValueChange = {
            passwordState = it
            onInput(it)
        },
        label = { Text(text = "Password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Icon",
                    tint = Color(0xFF4E515B)
                )
            }
        },
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier
            .width(320.dp)
            .height(65.dp),
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