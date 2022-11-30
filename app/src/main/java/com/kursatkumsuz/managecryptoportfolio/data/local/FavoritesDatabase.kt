package com.kursatkumsuz.managecryptoportfolio.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kursatkumsuz.managecryptoportfolio.domain.model.favorites.FavoritesEntity

@Database(entities = [FavoritesEntity::class], version = 2)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun getDao(): FavoritesDao

}