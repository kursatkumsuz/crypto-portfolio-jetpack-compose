package com.kursatkumsuz.domain.usecase.favorites

import com.kursatkumsuz.domain.model.FavoritesEntity
import com.kursatkumsuz.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(favorite : FavoritesEntity)  {
        favoritesRepository.insertFavorite(favorite)
    }
}