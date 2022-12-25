package com.kursatkumsuz.managecryptoportfolio.presentation.screens.portfolio

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PieChartModel
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio.DeletePortfolioUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio.GetPortfolioUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val getCoinUseCase: GetCoinUseCase,
    private val deletePortfolioUseCase: DeletePortfolioUseCase
) : ViewModel() {


    private var _selectedCoinList = mutableStateOf<List<CoinItem>>(listOf())
    val selectedCoinList = _selectedCoinList

    private var _selectedPortfolioList = mutableStateOf<List<PortfolioModel>>(listOf())
    val selectedPortfolioList = _selectedPortfolioList

    private var _chartList = mutableStateOf<List<PieChartModel>>(listOf())
    val chartList = _chartList

    private var _totalCurrentBalanceState = mutableStateOf(0.0)
    val totalCurrentBalanceState = _totalCurrentBalanceState

    private var _totalBuyingPriceState = mutableStateOf(0.0)
    val totalBuyingPriceState = _totalBuyingPriceState

    private var _profitState = mutableStateOf(0.0)
    val profitState = _profitState

    private var _loadingState = mutableStateOf(false)
    val loadingState = _loadingState

    private var _portfolioState = mutableStateOf<List<PortfolioModel>>(listOf())

    private var _coinState = mutableStateOf<List<CoinItem>>(listOf())

    init {
        refresh()
    }

    fun refresh() {
        loadPortfolio()
        loadCoinsFromApi()
    }


    private fun loadPortfolio() {
        // Load the portfolio data
        viewModelScope.launch {
            getPortfolioUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Success -> {
                        // Set the portfolio data and calculate the values
                        _portfolioState.value = convertSnapshotToPortfolioModels(response.data)
                        calculate()
                        // Set the loading state
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        // Set the loading state
                        _loadingState.value = true
                    }
                    is Response.Error -> {
                        // Set the loading state
                        _loadingState.value = false
                    }
                }
            }
        }
    }

    /**
    *Takes a [QuerySnapshot] and returns a list of [PortfolioModel] objects.
    *Creates a new [PortfolioModel] instance for each document in the snapshot and adds it to the list.
    *@param snapshot The [QuerySnapshot] to be processed.
    *@return A list of [PortfolioModel] objects.
     */
    private fun convertSnapshotToPortfolioModels(snapshot: QuerySnapshot?): List<PortfolioModel> {
        val list = ArrayList<PortfolioModel>()

        snapshot?.documents?.forEach { doc ->
            // Create a new PortfolioModel instance with the data from the current document
            val portfolio = PortfolioModel(
                doc.id,
                doc.get("name").toString(),
                doc.get("symbol").toString(),
                doc.get("buyingPrice").toString(),
                doc.get("amount").toString(),
                doc.get("totalPrice").toString(),
            )
            // Add the PortfolioModel to the list
            list.add(portfolio)

        }
        // Return the list of PortfolioModel objects
        return list
    }

    /**
     * Deletes a portfolio from the database.
     * @param docId The ID of the portfolio document to be deleted.
     */
    fun deletePortfolio(docId: String) {
        viewModelScope.launch {
            deletePortfolioUseCase.invoke(docId).collect {
                when (it) {
                    is Response.Success -> {
                        // Refresh after successful deletion
                        refresh()
                        // Set the loading state
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        // Set the loading state
                        _loadingState.value = true
                    }
                    is Response.Error -> {
                        // Set the loading state
                        _loadingState.value = false
                    }
                }
            }
        }
    }

    /**
     * Loads the list of coins from the API.
     * Sets the [coinState] and [loadingState] based on the response of the API.
     */
    private fun loadCoinsFromApi() {
        viewModelScope.launch {
            getCoinUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Success -> {
                        // Set the coinState to the data from the API
                        _coinState.value = response.data.data
                        calculate()
                        // Set the loading state
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        // Set the loading state
                        _loadingState.value = true

                    }
                    is Response.Error -> {
                        // Set the loading state
                        _loadingState.value = false
                    }
                }
            }
        }
    }

    private fun calculate() {
        setLists()
        calculateProfit()
    }

    private fun setLists() {

        // Initialize lists for pie chart model, portfolio model, selected coins, and prices
        val pieChartList = ArrayList<PieChartModel>()
        val selectedPortfolioList = ArrayList<PortfolioModel>()
        val selectedCoinList = ArrayList<CoinItem>()
        val lastPriceList = ArrayList<Double>()
        val buyingPriceList = ArrayList<Double>()

        // Find matching portfolios and add them to the appropriate lists
        addMatchingPortfolios(
            selectedPortfolioList,
            pieChartList,
            selectedCoinList,
            lastPriceList,
            buyingPriceList
        )

        // Set values for lists
        _chartList.value = pieChartList
        _selectedPortfolioList.value = selectedPortfolioList
        _selectedCoinList.value = selectedCoinList
        _totalCurrentBalanceState.value = lastPriceList.sum()
        _totalBuyingPriceState.value = buyingPriceList.sum()
    }

    /**
     * Adds matching portfolios to the provided lists
     * @param selectedPortfolioList List of selected portfolio models to be added
     * @param pieChartList List of pie chart models to be added
     * @param selectedCoinList List of selected coin items to be added
     * @param lastPriceList List of last prices to be added
     * @param buyingPriceList List of buying prices to be added
     */
    private fun addMatchingPortfolios(
        selectedPortfolioList: MutableList<PortfolioModel>,
        pieChartList: MutableList<PieChartModel>,
        selectedCoinList: MutableList<CoinItem>,
        lastPriceList: MutableList<Double>,
        buyingPriceList: MutableList<Double>
    ) {
        // Loop through portfolio state
        _portfolioState.value.forEach { portfolio ->
            // Loop through coin state
            _coinState.value.forEach { coin ->
                // Check if the symbol of the coin matches the symbol of the portfolio
                if (coin.symbol == portfolio.symbol) {
                    // Calculate the last price and buying price
                    val lastPrice = coin.quote.USD.price * portfolio.amount.toDouble()
                    val buyingPrice = portfolio.totalPrice.toDouble()
                    // Create a pie chart model and portfolio model for the matching portfolio
                    val pieChartModel = PieChartModel(lastPrice, coin.symbol)
                    val portfolioModel = PortfolioModel(
                        portfolio.id,
                        portfolio.name,
                        portfolio.symbol,
                        portfolio.buyingPrice,
                        portfolio.amount,
                        lastPrice.toString()
                    )
                    // Set values for lists
                    pieChartList.add(pieChartModel)
                    lastPriceList.add(lastPrice)
                    buyingPriceList.add(buyingPrice)
                    selectedCoinList.add(coin)
                    selectedPortfolioList.add(portfolioModel)
                }
            }
        }
    }


    /**
     * Calculate the profit for the portfolios
     */
    private fun calculateProfit() {
        // Calculate the difference between the current and buying prices
        val difference = _totalCurrentBalanceState.value - _totalBuyingPriceState.value
        // Calculate the profit as a percentage of the buying price
        val result = difference * 100 / _totalBuyingPriceState.value
        // Set the profit value
        _profitState.value = result
    }


}