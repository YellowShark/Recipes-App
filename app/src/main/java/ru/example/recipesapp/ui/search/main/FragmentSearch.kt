package ru.example.recipesapp.ui.search.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.model.search.ResponseSearch
import ru.example.recipesapp.ui.BaseFragment
import ru.example.recipesapp.utils.Status
import ru.example.recipesapp.utils.hideKeyboard


class FragmentSearch : BaseFragment(R.layout.fragment_search) {

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
                fetchRecipes()
                view?.let { activity?.hideKeyboard(it) }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        etSearchMeal.setOnClickListener {
            etSearchMeal.setText("")
            hideOptions()
            recipesAdapter.removeAll()
        }

        recipesAdapter = RecipesAdapter { item ->
            val actionDetails = FragmentSearchDirections.actionDestinationSearchToDestinationDetails()
            actionDetails.recipeId = item.id
            view?.let { Navigation.findNavController(it).navigate(actionDetails) }
        }

        val recipesLayoutManager = LinearLayoutManager(activity)

        val divider =
            DividerItemDecoration(requireContext(), recipesLayoutManager.orientation)

        recyclerView.apply {
            layoutManager = recipesLayoutManager
            adapter = recipesAdapter
            addItemDecoration(divider)
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
        if (viewModel.liveData.value?.data != null)
            if (viewModel.liveData.value?.data?.totalResults!! > recipesAdapter.itemCount) {
                fetchRecipes()
            }
    }

    private fun fetchRecipes() {
        val inputtedText = etSearchMeal.text.toString()
        if (inputtedText.isNotEmpty()) {
            viewModel.startSearchRecipe(request = inputtedText, num = recipesAdapter.itemCount + 10)
            onLoading()
        }
    }

    private fun initData() {
        viewModel.liveData.observe(
            viewLifecycleOwner, { event ->
                when (event.status) {
                    Status.SUCCESS -> onSuccess( data = event.data!!)
                    Status.EMPTY -> onEmptyData()
                    Status.ERROR -> onError()
                }
            })
    }

    private fun onEmptyData() {
        Toast.makeText(activity, "On your request nothing were found.", Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(data: ResponseSearch) {
        recipesAdapter.removeLoader()
        data.results?.let { recipesAdapter.setItems(meals = it) }
        showOptions()
    }
    private fun onLoading() {
        recipesAdapter.addLoader()
    }

    private fun onError() {
        Toast.makeText(activity, "Something went wrong. Try again later.", Toast.LENGTH_SHORT).show()
    }


    private fun showOptions() {
        search_options.visibility = View.VISIBLE
    }

    private fun hideOptions() {
        search_options.visibility = View.GONE
    }


}