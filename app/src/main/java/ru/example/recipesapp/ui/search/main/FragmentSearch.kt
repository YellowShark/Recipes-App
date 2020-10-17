package ru.example.recipesapp.ui.search.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.network.model.search.Recipe
import ru.example.recipesapp.data.network.model.search.ResponseSearch
import ru.example.recipesapp.ui.BaseFragment
import ru.example.recipesapp.utils.Event
import ru.example.recipesapp.utils.Status
import ru.example.recipesapp.utils.hideKeyboard


class FragmentSearch : BaseFragment(R.layout.fragment_search),
    AdapterView.OnItemSelectedListener {

    private val viewModel: SearchViewModel by viewModel()
    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        initData()
    }

    override fun onResume() {
        super.onResume()
        getFilters()
    }

    private fun getFilters() {
        arguments?.let {
            val args = FragmentSearchArgs.fromBundle(it)
            with(viewModel) {
                cuisine = args.filterCuisine
                diet = args.filterDiet
                type = args.filterType
            }
            reloadData()
        }
    }

    private fun initUi() {
        searchRecipe_et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchRecipe_et.text!!.isNotEmpty()) {
                fetchRecipes()
                view?.let { activity?.hideKeyboard(it) }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        searchRecipe_et.setOnClickListener {
            searchRecipe_et.setText("")
            recipesAdapter.removeAll()
        }
        filter_btn.setOnClickListener { openFiltersFragment() }

        initRecyclerView()
        initSpinners()
    }

    private fun openFiltersFragment() {
        val actionFilters =
            FragmentSearchDirections.actionFilters()
        view?.let { Navigation.findNavController(it).navigate(actionFilters) }
    }

    private fun initSpinners() {
        context?.let { ctx ->
            ArrayAdapter.createFromResource(
                ctx,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sortList_spinner.adapter = adapter
                sortList_spinner.onItemSelectedListener = this
            }
            ArrayAdapter.createFromResource(
                ctx,
                R.array.sort_directions,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sortDirList_spinner.adapter = adapter
                sortDirList_spinner.onItemSelectedListener = this
            }
        }
    }

    private fun initRecyclerView() {
        recipesAdapter.onItemClick = { item -> openFragmentDetails(item) }

        val recipesLayoutManager = LinearLayoutManager(activity)

        val divider =
            DividerItemDecoration(requireContext(), recipesLayoutManager.orientation)

        recipesList_rv.apply {
            layoutManager = recipesLayoutManager
            adapter = recipesAdapter
            addItemDecoration(divider)
        }

        recipesList_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null
                    && linearLayoutManager.findLastCompletelyVisibleItemPosition() == recipesAdapter.itemCount - 1
                ) {
                    //bottom of list!
                    loadMore()
                }
            }
        })
    }

    private fun openFragmentDetails(item: Recipe) {
        val actionDetails =
            FragmentSearchDirections.actionDestinationSearchToDestinationDetails()
        actionDetails.recipeId = item.id
        view?.let { Navigation.findNavController(it).navigate(actionDetails) }
    }

    private fun loadMore() {
        if (viewModel.liveData.value?.data != null)
            if (recipesAdapter.itemCount > 1 //to except situation with loading and double request
                && viewModel.liveData.value?.data?.totalResults!! > recipesAdapter.itemCount
            ) {
                fetchRecipes()
            }
    }

    private fun fetchRecipes() {
        val inputtedText = searchRecipe_et.text.toString()
        if (inputtedText.isNotEmpty()) {
            viewModel.startSearchRecipe(request = inputtedText, num = recipesAdapter.itemCount + 10)
            onLoading()
        }
    }

    private fun initData() {
        viewModel.liveData.observe(
            viewLifecycleOwner
        ) { event ->
            when (event.status) {
                Status.SUCCESS -> onSuccess(data = event.data!!)
                Status.EMPTY -> onEmptyData()
                Status.ERROR -> onError()
            }
        }
    }

    private fun onEmptyData() {
        recipesAdapter.removeLoader()
        searchRecipe_et.setText("")
        Toast.makeText(activity, getString(R.string.nothing_found_msg), Toast.LENGTH_SHORT).show()
    }

    private fun onSuccess(data: ResponseSearch) {
        data.results?.let {
            if (it.isNotEmpty()) {
                recipesAdapter.removeLoader()
                recipesAdapter.setItems(recipes = it)
            }
            else
                viewModel.liveData.value = Event.empty()
        }
    }

    private fun onLoading() {
        recipesAdapter.addLoader()
    }

    private fun onError() {
        recipesAdapter.removeLoader()
        Toast.makeText(activity, getString(R.string.error_msg), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.sortList_spinner -> viewModel.sortBy =
                parent.getItemAtPosition(position).toString()
            R.id.sortDirList_spinner -> viewModel.sortDir =
                parent.getItemAtPosition(position).toString()
        }
        reloadData()
    }

    private fun reloadData() {
        recipesAdapter.removeAll()
        fetchRecipes()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}