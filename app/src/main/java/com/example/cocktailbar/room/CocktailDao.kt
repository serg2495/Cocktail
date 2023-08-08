package com.example.cocktailbar.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktail")
    fun getAll():  Flow<List<Cocktail>>

    @Query("SELECT * FROM cocktail WHERE id = :id")
    fun get(id: Long):  Flow<Cocktail>

    @Insert
    suspend fun insert(cocktail: Cocktail)

    @Update
    suspend fun update(cocktail: Cocktail)
}