package com.kursatkumsuz.domain.repository

import com.kursatkumsuz.domain.model.FavoritesEntity

interface FavoritesRepository {

    suspend fun insertFavorite(favorite : FavoritesEntity)

    suspend fun deleteFavorite(favorite : FavoritesEntity)

    suspend fun getFavorites() : List<FavoritesEntity>
}