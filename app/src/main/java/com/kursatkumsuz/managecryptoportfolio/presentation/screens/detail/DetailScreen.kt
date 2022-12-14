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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CustomButton
import com.kursatkumsuz.managecryptoportfolio.presentation.components.common.CustomInputText
import com.kursatkumsuz.managecryptoportfolio.presentation.components.detail.BackButton
import com.kursatkumsuz.managecryptoportfolio.presentation.components.detail.InfoCard
import com.kursatkumsuz.managecryptoportfolio.util.FormatCoinPrice.Companion.formatPrice
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    navHostController: NavHostController,
    name: String,
    price: Float,
    symbol: String,
    lastDayChange: Float,
    lastOneHourChange: Float
) {
    val viewModel: DetailViewModel = hiltViewModel()
    val errorMessage = viewModel.errorMessage.value
    val formattedPrice = formatPrice(price.toDouble())
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet(name, symbol, price.toString(), viewModel) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Show the Toast if there is an error message
            if(errorMessage.isNotEmpty()) {
                Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_SHORT).show()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                BackButton(navController = navHostController)

                Spacer(modifier = Modifier.height(30.dp))

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

                Text(text = "$$formattedPrice", fontSize = 36.sp, color = Color.White)
                Spacer(modifier = Modifier.height(40.dp))
                DetailInfoSection(lastDayChange.toDouble(), lastOneHourChange.toDouble())
                Spacer(modifier = Modifier.height(80.dp))

                CustomButton(
                    text = "Add To Portfolio",
                    color = Color(0xFF4453CA),
                    textColor = Color.White,
                    onClick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) sheetState.hide()
                            else sheetState.show()
                        }
                    }) {}

                CustomButton(
                    text = "Add To Favorite",
                    onClick = {
                        val favorite = FavoritesEntity(symbol)
                        viewModel.addToFavorite(favorite)
                    }) {

                }
            }
        }
    }
}


@Composable
fun DetailInfoSection(lastDayChange: Double, lastOneHourChange: Double) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoCard(lastDayChange, "24h Change")
        InfoCard(lastOneHourChange, "1h Change")
    }
}


@Composable
fun BottomSheet(name: String, symbol: String, price: String, viewModel: DetailViewModel) {

    var priceState by remember { mutableStateOf(price) }
    var amountState by remember { mutableStateOf("0.0") }

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

        CustomInputText(
            keyboardType = KeyboardType.Decimal,
            initialText = priceState
        ) {
            priceState = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomInputText(
            keyboardType = KeyboardType.Decimal,
            initialText = amountState
        ) {
            amountState = it
        }

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            width = 260.dp,
            height = 50.dp,
            text = "Add to Portfolio",
            onClick = {
                val totalPrice = priceState.toFloat() * amountState.toFloat()

                val portfolio = PortfolioModel(
                    name = name,
                    symbol = symbol,
                    buyingPrice = priceState,
                    amount = amountState,
                    totalPrice = totalPrice.toString()
                )
                // Add the portfolio to the database
                viewModel.addToPortfolio(portfolio)
            }) {

        }
    }
}


