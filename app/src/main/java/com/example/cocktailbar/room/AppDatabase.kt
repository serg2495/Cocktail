package com.example.cocktailbar.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cocktail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}