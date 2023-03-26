package com.kursatkumsuz.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.kursatkumsuz.domain.model.onboarding.OnBoardingPage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val viewModel : OnBoardingViewModel = hiltViewModel()
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
    ) {
        HorizontalPager(
            modifier = Modifier.padding(top = 80.dp),
            count = pages.size,
            state = pagerState
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp),
            activeColor = Color.White,
            pagerState = pagerState
        )
        StartButton(pagerState = pagerState) {
            navController.popBackStack()
            if(viewModel.isLoggedInState.value) {
                navController.navigate("market_screen")
            } else {
                navController.navigate("signin_screen")
            }
        }
    }

}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(250.dp, 250.dp)
                .padding(top = 20.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "OnBoard Image"
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = onBoardingPage.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier.padding(30.dp),
            text = onBoardingPage.description,
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

@ExperimentalPagerApi
@Composable
fun StartButton(pagerState: PagerState, onClick: () -> Unit) {
    AnimatedVisibility(visible = pagerState.currentPage == 2) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {

            Button(
                modifier = Modifier.size(60.dp, 60.dp),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(99.dp)
            ) {
                Icon(imageVector =  Icons.Default.ArrowForward, contentDescription = "next icon")
            }
        }
    }
}

