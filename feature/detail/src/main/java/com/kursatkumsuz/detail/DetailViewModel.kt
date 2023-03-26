package com.kursatkumsuz.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatkumsuz.domain.model.FavoritesEntity
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.domain.usecase.favorites.AddFavoriteUseCase
import com.kursatkumsuz.domain.usecase.portfolio.AddToPortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val addToPortfolioUseCase: AddToPortfolioUseCase
) : ViewModel() {

    /**
     * Adds the given favorite to the database.
     * @param favorite The favorite to be added.
     */
    fun addToFavorite(favorite: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavoriteUseCase.invoke(favorite)
        }
    }


    fun addToPortfolio(
        portfolioModel: PortfolioModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addToPortfolioUseCase.invoke(portfolioModel)
        }
    }

}