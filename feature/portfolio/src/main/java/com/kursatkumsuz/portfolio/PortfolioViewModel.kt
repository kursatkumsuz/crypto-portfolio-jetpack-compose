package com.kursatkumsuz.portfolio

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.model.portfolio.PieChartModel
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.domain.usecase.portfolio.DeletePortfolioUseCase
import com.kursatkumsuz.domain.usecase.portfolio.GetPortfolioUseCase
import com.kursatkumsuz.util.Response
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
        viewModelScope.launch {
            getPortfolioUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Success -> {
                        response.data?.toObjects(PortfolioModel::class.java)?.let { list ->
                            _portfolioState.value = list
                        }
                        calculate()
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        _loadingState.value = true
                    }
                    is Response.Error -> {
                        _loadingState.value = false
                    }
                }
            }
        }
    }


    fun deletePortfolio(symbol: String) {
        viewModelScope.launch {
            deletePortfolioUseCase.invoke(symbol).collect {
                when (it) {
                    is Response.Success -> {
                        refresh()
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        _loadingState.value = true
                    }
                    is Response.Error -> {
                        _loadingState.value = false
                    }
                }
            }
        }
    }


    private fun loadCoinsFromApi() {
        viewModelScope.launch {
            getCoinUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _coinState.value = response.data.data
                        calculate()
                        _loadingState.value = false
                    }
                    is Response.Loading -> {
                        _loadingState.value = true

                    }
                    is Response.Error -> {
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

        val pieChartList = ArrayList<PieChartModel>()
        val selectedPortfolioList = ArrayList<PortfolioModel>()
        val selectedCoinList = ArrayList<CoinItem>()
        val lastPriceList = ArrayList<Double>()
        val buyingPriceList = ArrayList<Double>()

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


    private fun addMatchingPortfolios(
        selectedPortfolioList: MutableList<PortfolioModel>,
        pieChartList: MutableList<PieChartModel>,
        selectedCoinList: MutableList<CoinItem>,
        lastPriceList: MutableList<Double>,
        buyingPriceList: MutableList<Double>
    ) {
        _portfolioState.value.forEach { portfolio ->
            _coinState.value.forEach { coin ->
                if (coin.symbol == portfolio.symbol) {
                    val lastPrice = coin.quote.USD.price * portfolio.amount.toDouble()
                    val buyingPrice = portfolio.totalPrice.toDouble()
                    val pieChartModel = PieChartModel(
                        lastPrice,
                        coin.symbol
                    )
                    val portfolioModel = PortfolioModel(
                        portfolio.id,
                        portfolio.name,
                        portfolio.symbol,
                        portfolio.buyingPrice,
                        portfolio.amount,
                        lastPrice.toString()
                    )
                    pieChartList.add(pieChartModel)
                    lastPriceList.add(lastPrice)
                    buyingPriceList.add(buyingPrice)
                    selectedCoinList.add(coin)
                    selectedPortfolioList.add(portfolioModel)
                }
            }
        }
    }


    private fun calculateProfit() {
        val difference = _totalCurrentBalanceState.value - _totalBuyingPriceState.value
        val result = difference * 100 / _totalBuyingPriceState.value
        _profitState.value = result
    }
}