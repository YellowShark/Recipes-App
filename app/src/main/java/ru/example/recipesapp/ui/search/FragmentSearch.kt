package ru.example.recipesapp.ui.search

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.Status
import ru.example.recipesapp.ui.RecipesAdapter

class FragmentSearch : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()
    private var recipesAdapter: RecipesAdapter? = null

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
            this, { event ->
                Log.d("TAG", "initData: ${event.data}")
                when (event.status) {
                    Status.LOADING -> Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show()
                    Status.SUCCESS -> event.data!!.results?.let { recipesAdapter!!.addItems(it) }
                    Status.ERROR -> Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}