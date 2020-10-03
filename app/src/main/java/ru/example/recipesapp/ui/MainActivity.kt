package ru.example.recipesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.example.recipesapp.R
import ru.example.recipesapp.utils.RecipesAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: RecipesViewModel by lazy {
        ViewModelProviders.of(this).get(RecipesViewModel::class.java)
    }
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initData()
    }

    private fun initUi() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.title = "Meals"
        recyclerView.layoutManager = LinearLayoutManager(this)
        recipesAdapter = RecipesAdapter()
        recyclerView.adapter = recipesAdapter
    }

    private fun initData() {
        viewModel.callApi()
        viewModel.liveData.observe(this, { meals ->
            if (meals != null) {
                recipesAdapter.addItems(meals)
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}