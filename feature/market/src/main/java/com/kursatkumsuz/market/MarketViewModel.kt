package com.kursatkumsuz.market


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.domain.usecase.portfolio.GetPortfolioUseCase
import com.kursatkumsuz.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private var _cryptoList = mutableStateOf<List<CoinItem>>(listOf())
    val cryptoList = _cryptoList

    private var _popularList = mutableStateOf<List<CoinItem>>(listOf())
    val popularList = _popularList

    private var _currentBalance = mutableStateOf(0.0)
    val currentBalance = _currentBalance

    private var _loadingState = mutableStateOf(false)
    val loadingState = _loadingState

    private var portfolioList = mutableStateOf<List<PortfolioModel>>(listOf())


    init {
        refresh()
    }

    fun refresh() {
        loadPortfolio()
        loadCoins()
    }

    private fun loadPortfolio() {
        viewModelScope.launch {
            getPortfolioUseCase.invoke().collect { response ->
                when (response) {
                    is Response.Success -> {
                        response.data?.toObjects(PortfolioModel::class.java)?.let { list ->
                            portfolioList.value = list
                        }
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

    private fun loadCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoinUseCase.invoke().collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is Response.Success -> {
                            val list = it.data.data
                            _cryptoList.value = list
                            filterPopularCoins(list)
                            calculateCurrentBalance()
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
    }

    private fun filterPopularCoins(list: List<CoinItem>) {
        viewModelScope.launch {
            _popularList.value = list.filter { i -> i.quote.USD.price > 50.00 }
        }
    }

    private fun calculateCurrentBalance() {
        val list = ArrayList<Double>()
        portfolioList.value.forEach { portfolio ->
            _cryptoList.value.forEach { coin ->
                if (coin.symbol == portfolio.symbol) {
                    val coin = coin.quote.USD.price * portfolio.amount.toDouble()
                    list.add(coin)
                }
            }
        }
        _currentBalance.value = list.sum()
    }
}