package ru.example.recipesapp.ui.search.main

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
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
                startLoad()
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
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recipesAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == recipesAdapter.itemCount - 1) {
                    //bottom of list!
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        if (viewModel.liveData.value!!.data != null)
            if (viewModel.liveData.value?.data?.totalResults!! > recipesAdapter.itemCount) {
                startLoad()
            }
    }

    private fun startLoad() {
        val request = etSearchMeal.text.toString()
        if (request.isNotEmpty())
            viewModel.startSearchRecipe(request, recipesAdapter.itemCount + 10)
    }

    private fun initData() {
        viewModel.liveData.observe(
            this, { event ->
                when (event.status) {
                    Status.LOADING -> onLoading()
                    Status.SUCCESS -> onSuccess(event.data)
                    Status.ERROR -> onError()
                }
            })
    }

    private fun onSuccess(data: ResponseSearch?) {
        recipesAdapter.removeNull()
        data!!.results?.let { recipesAdapter.setItems(it) }
    }

    private fun onLoading() {
        //push null to adapter's data list -> adapter binds progressbar
        recipesAdapter.addNull()
    }

    private fun onError() {
        Toast.makeText(activity, "Error!!! Try again later.", Toast.LENGTH_SHORT).show()
    }
}