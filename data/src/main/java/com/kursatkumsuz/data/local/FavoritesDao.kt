package com.kursatkumsuz.data.local

import androidx.room.*
import com.kursatkumsuz.domain.model.FavoritesEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite : FavoritesEntity)

    @Delete
    suspend fun deleteFavorite(favorite : FavoritesEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getFavoritesList() : List<FavoritesEntity>
}