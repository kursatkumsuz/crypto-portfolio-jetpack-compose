package com.kursatkumsuz.managecryptoportfolio.presentation.screens.portfolio

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.portfolio.GetPortfolioUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
) : ViewModel() {

    private var _portfolioState = mutableStateOf<List<PortfolioModel>>(listOf())
    val portfolioState = _portfolioState


    init {
        loadPorfolio()
    }

    private fun loadPorfolio() {
        val list = ArrayList<PortfolioModel>()

        viewModelScope.launch {
            getPortfolioUseCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        val documents = it.data?.documents
                        documents?.let {
                            for (i in documents) {
                                val name = i.get("name")
                                val symbol = i.get("symbol")
                                val amount = i.get("amount")
                                val price = i.get("price")
                                println("Amount: $amount")
                                val portfolio = PortfolioModel(
                                    name.toString(),
                                    symbol.toString(),
                                    amount.toString(),
                                    price.toString()
                                )
                                list.addAll(listOf(portfolio))
                            }
                        }
                        _portfolioState.value = list
                    }
                    is Response.Loading -> {}
                    is Response.Error -> {}
                }
            }
        }
    }

}