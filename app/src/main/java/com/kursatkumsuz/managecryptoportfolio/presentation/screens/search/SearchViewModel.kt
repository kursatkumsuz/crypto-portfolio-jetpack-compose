package com.kursatkumsuz.managecryptoportfolio.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GetCoinUseCase
) : ViewModel() {

    private var coinList = mutableStateOf<List<CoinItem>>(listOf())
    var errorMessage = mutableStateOf("")
    private var _searchList = mutableStateOf<List<CoinItem>>(listOf())
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
            searchList.value = result
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