package com.kursatkumsuz.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.domain.model.coin.CoinItem
import com.kursatkumsuz.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: com.kursatkumsuz.domain.usecase.coin.GetCoinUseCase
) : ViewModel() {

    private var coinList =
        mutableStateOf<List<com.kursatkumsuz.domain.model.coin.CoinItem>>(listOf())
    var errorMessage = mutableStateOf("")
    private var _searchList =
        mutableStateOf<List<com.kursatkumsuz.domain.model.coin.CoinItem>>(listOf())
    var searchList = _searchList

    init {
        loadCoins()
    }

    fun searchCoinList(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _searchList.value = listOf()
                return@launch
            }
            val result = coinList.value.filter {
                it.name.contains(
                    query.trim(),
                    ignoreCase = true
                ) || it.symbol.contains(query.trim(), ignoreCase = true)
            }
            _searchList.value = result
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            useCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        coinList.value = it.data.data
                        errorMessage.value = ""
                    }
                    is Response.Error -> {
                        errorMessage.value = it.msg
                    }
                }
            }
        }
    }

}