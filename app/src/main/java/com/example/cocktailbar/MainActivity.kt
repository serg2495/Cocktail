package com.example.cocktailbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailbar.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel((application as App).db.cocktailDao()) as T
        }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.allCocktail.collect { cList ->
                if (cList.isNotEmpty()) {
                    binding.ivMain.visibility = View.GONE
                    binding.ivDown.visibility = View.GONE
                    binding.tvAdd.visibility = View.GONE
                    val list = cList.map { ItemViewModel(
                        id = it.id,
                        img = it.img,
                        title = it.title
                    ) }
                    binding.recycler.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    binding.recycler.adapter = Adapter(list) {
                        startActivity(Intent(this@MainActivity, DetalsActivity::class.java).apply {
                            putExtra("id", it)
                        })
                    }
                }
            }
        }
    }
}