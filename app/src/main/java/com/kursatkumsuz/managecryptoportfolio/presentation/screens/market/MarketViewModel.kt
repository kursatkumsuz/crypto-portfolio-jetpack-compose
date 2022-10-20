package com.kursatkumsuz.managecryptoportfolio.presentation.screens.market

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val coinUseCase: GetCoinUseCase
) : ViewModel() {

   // var coinList = mutableStateOf<List<CoinItem>>(listOf())
    var coinLiveData = MutableLiveData<List<CoinItem>>()
    var errorMessage =  mutableStateOf("")

    init {
        loadCoins()
    }

    fun loadCoins() {
        viewModelScope.launch {
            val result = coinUseCase.getCoinList()
            when (result.status) {
                Status.SUCCESS -> {
                    coinLiveData.value = result.data?.data
                    errorMessage.value = ""
                }
                Status.ERROR -> {
                    errorMessage.value = result.message!!
                }
            }
        }
    }

}