package com.kursatkumsuz.managecryptoportfolio.domain.model.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    val name: String,
    val symbol: String,
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int? = null
)
