package ru.example.recipesapp.ui.search.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.Status
import ru.example.recipesapp.data.network.model.search.ResponseSearch
import ru.example.recipesapp.utils.hideKeyboard

class FragmentSearch : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        initData()
    }

    private fun initUi() {
        etSearchMeal.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.startSearchRecipe(request = etSearchMeal.text.toString())
                view?.let { activity?.hideKeyboard(it) }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        recipesAdapter = RecipesAdapter { item ->
            val actionDetails = FragmentSearchDirections.actionDestinationSearchToDestinationDetails()
            actionDetails.recipeId = item.id
            view?.let { Navigation.findNavController(it).navigate(actionDetails) }
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recipesAdapter
    }

    private fun initData() {
        viewModel.liveData.observe(
            this, { event ->
                Log.d("TAG", "initData: ${event.data}")
                when (event.status) {
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> showData(event.data)
                    Status.ERROR -> onError()
                }
            })
    }

    private fun showData(data: ResponseSearch?) {
        search_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        data!!.results?.let { recipesAdapter.setItems(it) }
    }

    private fun onLoading() {
        search_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    private fun onError() {
        Toast.makeText(activity, "Error!!! Try again later.", Toast.LENGTH_SHORT).show()
        search_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
    }
}