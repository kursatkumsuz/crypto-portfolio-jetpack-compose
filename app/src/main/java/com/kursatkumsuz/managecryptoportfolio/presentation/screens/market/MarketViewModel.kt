package com.kursatkumsuz.managecryptoportfolio.presentation.screens.market


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val coinUseCase: GetCoinUseCase
) : ViewModel() {

    private var _cryptoList = mutableStateOf<List<CoinItem>>(listOf())
    val cryptoList = _cryptoList

    init {
        loadCoins()
    }

    private fun loadCoins() {
        viewModelScope.launch {
            coinUseCase.invoke().collect {
                when(it) {
                    is Response.Success -> {
                        _cryptoList.value = it.data.data
                    }
                    is Response.Loading -> {}
                    is Response.Error -> {}

                }
            }
        }
    }

}