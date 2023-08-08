package com.example.cocktailbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailbar.room.Cocktail
import com.example.cocktailbar.room.CocktailDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val cocktailDao: CocktailDao) : ViewModel() {
    val allCocktail = cocktailDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getCocktail(id: Long) = cocktailDao.get(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onAdd(cocktail: Cocktail) {
        viewModelScope.launch {
            cocktailDao.insert(cocktail)
        }
    }

    fun onUpdate(cocktail: Cocktail) {
        viewModelScope.launch {
            val index = allCocktail.value.indexOfFirst { it.id == cocktail.id }
            allCocktail.value[index].let {
                cocktailDao.update(cocktail)
            }
        }
    }
}