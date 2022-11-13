package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.kursatkumsuz.managecryptoportfolio.data.local.FavoritesDao
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity
import com.kursatkumsuz.managecryptoportfolio.domain.repository.FavoritesRepository

class FavoritesRepositoryImp(private val dao: FavoritesDao) : FavoritesRepository {

    override suspend fun insertFavorite(favorite: FavoritesEntity) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoritesEntity) {
        dao.deleteFavorite(favorite)
    }

    override suspend fun getFavorites(): List<FavoritesEntity> {
        return dao.getFavoritesList()
    }
}