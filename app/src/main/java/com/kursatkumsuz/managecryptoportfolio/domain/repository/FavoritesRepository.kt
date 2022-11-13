package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity

interface FavoritesRepository {

    suspend fun insertFavorite(favorite : FavoritesEntity)

    suspend fun deleteFavorite(favorite : FavoritesEntity)

    suspend fun getFavorites() : List<FavoritesEntity>
}