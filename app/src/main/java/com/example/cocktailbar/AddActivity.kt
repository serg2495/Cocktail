package com.example.cocktailbar

import android.app.Dialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Base64
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailbar.Utils.imgToString
import com.example.cocktailbar.databinding.ActivityAddBinding
import com.example.cocktailbar.databinding.DialogBinding
import com.example.cocktailbar.room.Cocktail
import java.io.ByteArrayOutputStream

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val viewModel: MainViewModel by viewModels {object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel((application as App).db.cocktailDao()) as T
        }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivAdd.setOnClickListener { showDialog() }
        binding.btnSave.setOnClickListener { save() }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        val dialogBinding = DialogBinding.inflate(dialog.layoutInflater)
        dialog.apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
        }
        dialogBinding.btnSave.setOnClickListener {
            if (dialogBinding.tietTitle.text.toString() != "") {
                val item = IngredientView(this) {
                    binding.ll.removeView(it)
                }
                item.text = dialogBinding.tietTitle.text.toString()
                binding.ll.addView(item)
                dialog.dismiss()
            } else dialogBinding.tilTitle.error = resources.getString(R.string.add_title)
        }
        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun save() {
        if (binding.tietTitle.text.toString() == "") binding.tilTitle.error =
            resources.getString(R.string.add_title)
        else if (binding.ll.childCount == 0) Toast.makeText(
            this,
            resources.getString(R.string.emptyList),
            Toast.LENGTH_LONG
        ).show()
        else {
            val list = mutableListOf<String>()
            for (i in 0 until binding.ll.childCount) {
                 val a = binding.ll.getChildAt(i) as IngredientView
                 list.add(a.text)
            }
            viewModel.onAdd(Cocktail(
                id = System.currentTimeMillis(),
                img = imgToString(binding.ivPhoto),
                title = binding.tietTitle.text.toString(),
                description = binding.tielDescription.text.toString(),
                ingredient = list.joinToString(separator = "|"),
                recipe = binding.tielRecipe.text.toString()
                ))
            onBackPressed()
        }
    }
}