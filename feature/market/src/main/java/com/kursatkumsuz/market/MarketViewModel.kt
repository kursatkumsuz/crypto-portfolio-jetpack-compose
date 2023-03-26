package com.kursatkumsuz.market


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val coinUseCase: GetCoinUseCase
) : ViewModel() {

    private var _cryptoList = mutableStateOf<List<CoinItem>>(listOf())
    val cryptoList = _cryptoList

    private var _popularList = mutableStateOf<List<CoinItem>>(listOf())
    val popularList = _popularList

    private var _loadingState = mutableStateOf(false)
    val loadingState = _loadingState

    private var _errorMessage = mutableStateOf("")
    val errorMessage = _errorMessage

    init {
        refresh()
    }

    fun refresh() {
        loadCoins()
    }

    private fun loadCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            coinUseCase.invoke().collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is Response.Success -> {
                            val list = it.data.data
                            _cryptoList.value = list
                            filterPopularCoins(list)
                            _loadingState.value = false
                        }
                        is Response.Loading -> {
                            _loadingState.value = true
                        }
                        is Response.Error -> {
                            _loadingState.value = false
                            _errorMessage.value = it.msg
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
}