package com.kursatkumsuz.managecryptoportfolio.presentation.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CustomCircularProgress
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CustomInputText
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CustomPasswordText
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.Toast
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

        var emailState by remember { mutableStateOf("") }
        var passwordState by remember { mutableStateOf("") }

        Text(text = "Welcome", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please Sign in your account!",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(60.dp))

        CustomInputText(labelText = "Email", backgroundColor = Color(0xFF252A34)) {
            emailState = it
        }

        Spacer(modifier = Modifier.height(20.dp))

        CustomPasswordText { passwordState = it }

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            modifier = Modifier.size(320.dp, 65.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4453CA)),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                viewModel.signIn(emailState, passwordState)
            }) {
            Text(text = "Sign In", color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            CheckSignInState(viewModel, navController)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't you have an account?", fontSize = 14.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.width(2.dp))
            TextButton(onClick = { navController.navigate("signup_screen") }) {
                Text(text = "Sign Up", fontSize = 14.sp, color = Color.Blue)

            }
        }

    }
}


@Composable
fun CheckSignInState(viewModel: SignInViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) CustomCircularProgress()
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











