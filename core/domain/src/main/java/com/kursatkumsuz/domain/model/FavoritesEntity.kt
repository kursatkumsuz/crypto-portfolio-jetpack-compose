package com.kursatkumsuz.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    val symbol: String,
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int? = null
)