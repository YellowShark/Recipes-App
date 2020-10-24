package ru.example.recipesapp.ui.search.main

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.example.recipesapp.R
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.network.search.response.Recipe
import ru.example.recipesapp.ui.BaseFragment
import ru.example.recipesapp.utils.*


class FragmentSearch : BaseFragment(R.layout.fragment_search),
    AdapterView.OnItemSelectedListener, View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView
    private val viewModel: SearchViewModel by viewModel()
    private val recipesListAdapter by lazy { RecipesAdapter() }
    private val eventObserver = Observer<Event<CurrentSearchResults>> { event ->
        Log.d("TAG", "fragment: ${event.status.name}")
        when (event.status) {
            Status.LOADING -> showLoader()
            Status.PAGING -> addLoader()
            Status.SUCCESS -> {
                removeLoader()
                recipesListAdapter.setItems(recipes = event.data!!.results)
            }
            Status.EMPTY -> {
                removeLoader()
                recipesListAdapter.removeAll()
                searchView.setQuery("", false)
                showNothingFoundMsg()
            }
            Status.ERROR -> {
                removeLoader()
                showErrorMsg()
            }
            Status.DEFAULT -> {
                //TODO
                //show something interesting))
            }
        }
    }

    private fun showLoader() {
        loading_view.visibility = View.VISIBLE
        content_sv.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        observeViewModel()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: ")
        super.onResume()
        searchView.setQuery(viewModel.query.text, true)
        getFilters()
    }

    override fun onDestroy() {
        viewModel.deleteLastSearchQuery()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    private fun observeViewModel() {
        viewModel.eventLive.observe(viewLifecycleOwner, eventObserver)
    }

    private fun initUi() {
        initListeners()
        initRecyclerView()
        initSpinners()
        initSearchView()
    }

    private fun initSearchView() {
        searchItem = search_toolbar.menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        with(searchView) {
            focusable = SearchView.FOCUSABLE
            isIconified = false
            queryHint = getString(R.string.search_here)
            setOnQueryTextListener(searchTextListener())
            setOnCloseListener { searchItem.collapseActionView() }
            //TODO searchAdapter = ...
            /*setOnSuggestionListener(searchAdapterListener())*/
        }
    }

    private fun searchTextListener() = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            with(searchView) {
                clearFocus()
                searchItem.collapseActionView()
                if (query != null && query.length > 2) {
                    viewModel.query.text = query
                }
                setQuery("", false)
            }
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            //searchAdapter.changeCursor(null)
            if (newText.length >= 2) {
                viewModel.query.text = newText
                searchRecipe()
            }
            return true
        }
    }

    /*private fun searchAdapterListener() = object : SearchView.OnSuggestionListener {
        override fun onSuggestionSelect(id: Int): Boolean = false
        override fun onSuggestionClick(id: Int): Boolean {
            //not yet implemented
            return true
        }
    }*/

    private fun initListeners() {
        filter_btn.setOnClickListener(this)
    }

    private fun initSpinners() {
        context?.let { ctx ->
            ArrayAdapter.createFromResource(
                ctx,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
            ).also { sortByAdapter ->
                sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sortByList_spinner.apply {
                    adapter = sortByAdapter
                    setSelection(sortByAdapter.getPosition(viewModel.query.sortBy))
                    onItemSelectedListener = this@FragmentSearch
                }
            }
            ArrayAdapter.createFromResource(
                ctx,
                R.array.sort_directions,
                android.R.layout.simple_spinner_item
            ).also { sortDirAdapter ->
                sortDirAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sortDirList_spinner.apply {
                    adapter = sortDirAdapter
                    setSelection(sortDirAdapter.getPosition(viewModel.query.sortDir))
                    onItemSelectedListener = this@FragmentSearch
                }
                sortDirList_spinner.adapter = sortDirAdapter
                sortDirList_spinner.onItemSelectedListener = this
            }
        }
    }

    private fun initRecyclerView() {
        recipesListAdapter.onItemClick = { item -> openFragmentDetails(item) }

        val recipesLayoutManager = LinearLayoutManager(activity)

        val divider =
            DividerItemDecoration(requireContext(), recipesLayoutManager.orientation)

        recipesList_rv.apply {
            layoutManager = recipesLayoutManager
            adapter = recipesListAdapter
            addItemDecoration(divider)
        }

        recipesList_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null
                    && recipesListAdapter.itemCount >= PAGE_SIZE
                    && linearLayoutManager.findLastCompletelyVisibleItemPosition() == recipesListAdapter.itemCount - 2
                ) {
                    viewModel.loadMore()
                }
            }
        })
    }

    private fun openFiltersFragment() {
        val actionFilters =
            FragmentSearchDirections.actionFilters()
        view?.let { Navigation.findNavController(it).navigate(actionFilters) }
    }

    private fun getFilters() {
        arguments?.let {
            val args = FragmentSearchArgs.fromBundle(it)
            if (args.filterCuisine.isNotEmpty() || args.filterDiet.isNotEmpty() || args.filterType.isNotEmpty()) {
                with(viewModel.query) {
                    cuisine = args.filterCuisine
                    diet = args.filterDiet
                    type = args.filterType
                }
                reloadData()
            }
        }
    }

    private fun openFragmentDetails(item: Recipe) {
        val actionDetails =
            FragmentSearchDirections.actionDestinationSearchToDestinationDetails()
        actionDetails.recipeId = item.id
        view?.let { Navigation.findNavController(it).navigate(actionDetails) }
    }

    private fun searchRecipe() {
        Log.d("TAG", "searchRecipe: ")
        with(viewModel) {
            searchRecipes()
        }
    }

    private fun addLoader() {
        recipesListAdapter.addLoader()
    }

    private fun removeLoader() {
        if (content_sv.visibility == View.GONE) {
            content_sv.visibility = View.VISIBLE
            loading_view.visibility = View.GONE
            return
        }
        recipesListAdapter.removeLoader()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        with(viewModel.query) {
            when (parent?.id) {
                R.id.sortByList_spinner -> sortBy =
                    parent.getItemAtPosition(position).toString()
                R.id.sortDirList_spinner -> sortDir =
                    parent.getItemAtPosition(position).toString()
            }
        }
        reloadData()
    }

    private fun showErrorMsg() {
        Toast.makeText(activity, getString(R.string.error_msg), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showNothingFoundMsg() {
        Toast.makeText(activity, getString(R.string.nothing_found_msg), Toast.LENGTH_SHORT)
            .show()
    }


    private fun reloadData() {
        recipesListAdapter.removeAll()
        searchRecipe()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.filter_btn -> openFiltersFragment()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchRecipe()
            view?.let { activity?.hideKeyboard(it) }
            return true
        }
        return false
    }
}