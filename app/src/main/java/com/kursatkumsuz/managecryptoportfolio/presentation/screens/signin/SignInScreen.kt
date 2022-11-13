package com.kursatkumsuz.managecryptoportfolio.presentation.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.R
import com.kursatkumsuz.managecryptoportfolio.presentation.components.CustomCircularProgress
import com.kursatkumsuz.managecryptoportfolio.presentation.components.Toast
import com.kursatkumsuz.managecryptoportfolio.util.Response

@Composable
fun SignInScreen(navController: NavHostController) {

    val viewModel: SignInViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val passwordVisibility = remember { mutableStateOf(false) }

        val icon = if (passwordVisibility.value) {
            painterResource(id = R.drawable.ic_visible)
        } else {
            painterResource(id = R.drawable.ic_invisible)
        }

        Text(text = "Welcome", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please Sign in your account!",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(60.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text(text = "E-mail") },
            singleLine = true,
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

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon",
                        tint = Color(0xFF4E515B)
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value)
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

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            modifier = Modifier.size(320.dp, 65.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4453CA)),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                viewModel.signIn(emailState.value, passwordState.value)
            }) {
            Text(text = "Sign In", color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            CheckSignInState(viewModel, navController)
        }

        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't you have an account?" , fontSize = 14.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.width(2.dp))
            TextButton(onClick = { navController.navigate("signup_screen") }) {
                Text(text = "Sign Up" , fontSize = 14.sp, color = Color.Blue)

            }
        }

    }
}

@Composable
fun CheckSignInState(viewModel: SignInViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    if(isLoading.value) CustomCircularProgress()
    LaunchedEffect(key1 = Unit) {

        viewModel.signInFlow.collect {
            when (it) {
                is Response.Success -> {
                    isLoading.value = false
                    navController.popBackStack()
                    navController.navigate("market_screen")
                }

                is Response.Loading -> {
                    isLoading.value = true
                }

                is Response.Error -> {
                    isLoading.value = false
                    Toast(context, it.msg)
                }
            }
        }
    }
}









