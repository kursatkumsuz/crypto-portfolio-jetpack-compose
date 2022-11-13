package com.kursatkumsuz.managecryptoportfolio.presentation.screens.detail

import android.widget.Toast
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
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.presentation.components.LoadingCircularProgress
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.launch
import javax.net.ssl.SSLEngineResult.Status

@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    name: String,
    price: Float,
    symbol: String
) {
    val viewModel: DetailViewModel = hiltViewModel()
    val formattedPrice = formatPrice(price.toDouble())
    val addFavoriteState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet(name, formattedPrice, viewModel) },
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, fontSize = 36.sp, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier
                    .size(90.dp, 30.dp)
                    .background(MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = symbol, fontSize = 20.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(text = formattedPrice, fontSize = 36.sp, color = Color.White)

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                modifier = Modifier.size(320.dp, 65.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF4453CA)),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                }
            ) {
                Text(text = "Add Portfolio", color = Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                modifier = Modifier.size(320.dp, 65.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE1E2EB)),
                shape = RoundedCornerShape(20.dp),
                onClick = { addFavoriteState.value = true }) {
                Text(text = "Add Favorites", color = Color.DarkGray)

                if (addFavoriteState.value) addToFavorites(name, symbol, viewModel)
            }
        }
    }
}

@Composable
private fun addToFavorites(coinName: String, coinSymbol: String, viewModel: DetailViewModel) {
    val favorite = FavoritesEntity(coinName, coinSymbol)

    LaunchedEffect(key1 = Unit) {
        viewModel.addToFavorite(favorite)
    }
}

@Composable
private fun addToPortfolio(
    coinName: String,
    coinSymbol: String,
    coinPrice: String,
    viewModel: DetailViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.addToPortfolio(coinName, coinSymbol, coinPrice)
    }
}

@Composable
private fun CheckAddPortfolioState(viewModel: DetailViewModel) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.addToPortfolioFlow.collect {
            when (it) {
                is Response.Success -> {
                    Toast.makeText(context, "Succesfully Added!", Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {}
                is Response.Error -> {
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Composable
fun BottomSheet(name: String, price: String, viewModel: DetailViewModel) {

    val priceState = remember { mutableStateOf(price) }
    val addPortfolioState = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191C24)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = priceState.value,
            onValueChange = { priceState.value = it },
            singleLine = true,
            modifier = Modifier
                .width(320.dp)
                .height(65.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color(0xFF95A6C5)
            ),
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.size(260.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE1E2EB)),
            shape = RoundedCornerShape(20.dp),
            onClick = { addPortfolioState.value = true }) {
            Text(text = "Add Portfolio", color = Color.DarkGray)
            if (addPortfolioState.value) {
                addToPortfolio(name, name, priceState.value, viewModel)
                CheckAddPortfolioState(viewModel)
            }
        }
    }
}

