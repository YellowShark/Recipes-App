package ru.example.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recipes.*
import ru.example.recipesapp.R
import ru.example.recipesapp.utils.Adapter

class RecipesActivity : AppCompatActivity() {

    private lateinit var recipesAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recipesAdapter = Adapter()
        recyclerView.adapter = recipesAdapter
    }
}