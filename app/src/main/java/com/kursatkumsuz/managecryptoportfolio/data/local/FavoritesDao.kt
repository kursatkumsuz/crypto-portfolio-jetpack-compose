package com.kursatkumsuz.managecryptoportfolio.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite : FavoritesEntity)

    @Delete
    suspend fun deleteFavorite(favorite : FavoritesEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getFavoritesList() : List<FavoritesEntity>
}