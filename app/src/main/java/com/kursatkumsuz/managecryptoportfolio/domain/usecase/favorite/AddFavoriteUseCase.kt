package com.kursatkumsuz.managecryptoportfolio.domain.usecase.favorite

import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend operator fun invoke(favorite : FavoritesEntity)  {
        favoritesRepository.insertFavorite(favorite)
    }
}