package ru.example.recipesapp.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.ui.RecipesAdapter

class FragmentSearch : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        initData()
    }

    private fun initUi() {
        recipesAdapter = RecipesAdapter { item ->
            Toast.makeText(activity, "${item.title} was clicked", Toast.LENGTH_SHORT)
                .show()
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recipesAdapter
    }

    private fun initData() {
        viewModel.startSearchRecipe(request = "pasta")
        viewModel.liveData.observe(
            this, { meals ->
                if (meals != null) {
                    recipesAdapter.addItems(meals)
                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
    }
}