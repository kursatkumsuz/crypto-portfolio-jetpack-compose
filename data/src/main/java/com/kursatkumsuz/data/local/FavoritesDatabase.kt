package com.kursatkumsuz.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.kursatkumsuz.domain.model.FavoritesEntity

@Database(
    entities = [FavoritesEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(
            from = 1, to = 2, spec = FavoritesDatabase.MyAutoMigration::class
        )
    ]

)
abstract class FavoritesDatabase : RoomDatabase() {

    @DeleteColumn(tableName = "favorites",columnName = "name")
    class MyAutoMigration : AutoMigrationSpec

    abstract fun getDao(): FavoritesDao

}