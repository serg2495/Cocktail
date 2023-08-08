package com.example.cocktailbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.cocktailbar.databinding.IngredientViewBinding

class IngredientView @JvmOverloads constructor(
    private val context: Context,
    private val onClick: (View) -> Unit
) : LinearLayout(context) {

    private val binding = IngredientViewBinding.inflate(LayoutInflater.from(context), this)

    var text = ""
        set(value) {
            field = value
            binding.tvIngredient.text = value
        }

    init {
        binding.ivDelete.setOnClickListener {
            onClick(it)
        }
    }
}