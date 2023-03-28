package com.kursatkumsuz.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.domain.model.FavoritesEntity
import com.kursatkumsuz.ui.components.common.CustomButton
import com.kursatkumsuz.ui.components.common.CustomInputText
import com.kursatkumsuz.detail.component.BackButton
import com.kursatkumsuz.detail.component.InfoCard
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.util.FormatCoinPrice.Companion.formatPrice
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
    val formattedPrice = formatPrice(price.toDouble())
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheet(
                name,
                symbol,
                price.toString(),
                viewModel,
                navHostController
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                        Text(text = symbol, fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Black)
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
                        navHostController.navigate("favorites_screen")
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
fun BottomSheet(
    name: String,
    symbol: String,
    price: String,
    viewModel: DetailViewModel,
    navHostController: NavHostController
) {

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
            keyboardType = KeyboardType.Number,
            initialText = priceState
        ) {
            priceState = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomInputText(
            keyboardType = KeyboardType.Number,
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
                viewModel.addToPortfolio(portfolio)
                navHostController.navigate("portfolio_screen")
            }) {

        }
    }
}


