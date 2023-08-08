package com.example.cocktailbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cocktailbar.databinding.ActivityDetalsBinding
import com.example.cocktailbar.databinding.TextBinding

class DetalsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalsBinding
    private val viewModel: MainViewModel by viewModels {object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel((application as App).db.cocktailDao()) as T
        }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("id", 0)

        lifecycleScope.launchWhenCreated {
            viewModel.getCocktail(id).collect { cocktail ->
                cocktail?.let { cocktail1 ->
                    binding.title.text = cocktail1.title
                    binding.descritpion.text = cocktail1.description
                    binding.recipe.text = cocktail1.recipe
                    cocktail1.ingredient.split("|").forEach {
                        val item = TextBinding.inflate(layoutInflater)
                        item.text.text = it
                        binding.ll.addView(item.root)
                    }
                }
            }
        }
    }
}