package com.kursatkumsuz.signup

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
import com.kursatkumsuz.ui.components.common.CustomCircularProgress
import com.kursatkumsuz.ui.components.common.CustomInputText
import com.kursatkumsuz.ui.components.CustomPasswordText
import com.kursatkumsuz.ui.components.common.Toast

@Composable
fun SignUpScreen(navController: NavHostController) {


    val viewModel: SignUpViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        var nameState by remember { mutableStateOf("") }
        var emailState by remember { mutableStateOf("") }
        var passwordState by remember { mutableStateOf("") }

        Text(
            text = "Create an account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please fill all informations!",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(60.dp))

        CustomInputText(labelText = "Name", backgroundColor = Color(0xFF252A34)) { nameState = it }

        Spacer(modifier = Modifier.height(20.dp))

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
                viewModel.signUp(nameState,emailState, passwordState)
            }) {
            Text(text = "Create", color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            CheckSignUpState(viewModel, navController)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Have an account?", fontSize = 14.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.width(2.dp))
            TextButton(onClick = { navController.navigate("signin_screen") }) {
                Text(text = "Sign In", fontSize = 14.sp, color = Color.Blue)

            }
        }

    }
}

@Composable
fun CheckSignUpState(viewModel: SignUpViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }

    if (isLoading.value) CustomCircularProgress()

    LaunchedEffect(key1 = Unit) {

        viewModel.signUpFlow.collect {
            when (it) {
                is com.kursatkumsuz.util.Response.Success -> {
                    isLoading.value = false
                    navController.popBackStack()
                    navController.navigate("signin_screen")
                }

                is com.kursatkumsuz.util.Response.Loading -> {
                    isLoading.value = true
                }

                is com.kursatkumsuz.util.Response.Error -> {
                    isLoading.value = false
                    Toast(context, it.msg)
                }
            }
        }
    }
}

