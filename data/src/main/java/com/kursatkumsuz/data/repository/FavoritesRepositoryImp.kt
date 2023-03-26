package com.kursatkumsuz.data.repository

import com.kursatkumsuz.data.local.FavoritesDao
import com.kursatkumsuz.domain.model.FavoritesEntity
import com.kursatkumsuz.domain.repository.FavoritesRepository



class FavoritesRepositoryImp(private val dao: FavoritesDao) :
    FavoritesRepository {

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