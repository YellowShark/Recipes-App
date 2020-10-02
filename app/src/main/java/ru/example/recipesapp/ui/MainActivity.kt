package ru.example.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.example.recipesapp.R
import ru.example.recipesapp.utils.RecipesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.title = "Meals"

        recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel::class.java)
        recipesViewModel.callApi()

        recipesViewModel.liveData.observe(this, { meals ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            if (meals != null) {
                recipesAdapter = RecipesAdapter(meals)
                recyclerView.adapter = recipesAdapter
            }
        })
    }
}