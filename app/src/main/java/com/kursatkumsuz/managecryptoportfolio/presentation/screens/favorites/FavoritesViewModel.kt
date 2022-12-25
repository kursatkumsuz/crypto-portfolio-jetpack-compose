package com.kursatkumsuz.managecryptoportfolio.presentation.screens.favorites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.managecryptoportfolio.domain.model.coin.CoinItem
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.coin.GetCoinUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.favorite.DeleteFavoriteUseCase
import com.kursatkumsuz.managecryptoportfolio.domain.usecase.favorite.GetFavoritesUseCase
import com.kursatkumsuz.managecryptoportfolio.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private var _coinList = mutableStateOf<List<CoinItem>>(listOf())
    var coinList = _coinList

    private var _favoriteList = mutableStateOf<List<FavoritesEntity>>(listOf())
    var favoriteList = _favoriteList

    private var _loadingState = mutableStateOf(false)
    var loadingState = _loadingState

    init {
        refreshData()
    }

    fun refreshData() {
        loadFavorites()
        loadCoins()
    }

    /**
     * Loads favorites from RoomDB
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        _favoriteList.value = it.data
                    }
                    else -> {}
                }
            }
        }
    }

    private fun loadCoins() {
        val selectedList = ArrayList<CoinItem>()
        viewModelScope.launch {
            getCoinUseCase.invoke().collect {
                when (it) {
                    is Response.Success -> {
                        it.data.data.forEach { coin ->
                            favoriteList.value.forEach { favorite ->
                                if (coin.symbol == favorite.symbol) {
                                    selectedList.add(coin)
                                }
                            }
                        }
                        _coinList.value = selectedList
                        loadingState.value = false
                    }
                    is Response.Loading -> {
                        loadingState.value = true
                    }
                    is Response.Error -> {
                        loadingState.value = false
                    }
                }
            }
        }
    }

    /**
    *Deletes a given favorite from the database.
    *@param favorite: The favorite to delete.
     */
    fun deleteFavorite(favorite: FavoritesEntity) {
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(favorite)
            refreshData()

        }
    }
}