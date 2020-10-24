package ru.example.recipesapp.ui.search.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery
import ru.example.recipesapp.data.repository.RecipesRepository
import ru.example.recipesapp.utils.Event
import ru.example.recipesapp.utils.PAGE_SIZE
import ru.example.recipesapp.utils.TAG

class SearchViewModel(
    private val repository: RecipesRepository
) : ViewModel() {
    var query = SearchQuery()
        set(value) {
            field = value
            updateQuery()
        }

    val eventLive = MutableLiveData<Event<CurrentSearchResults>>()

    init {
        eventLive.value = Event.default()
        query = getLastSearchQuery()
    }

    fun searchRecipes() {
        Log.d(TAG, "searchRecipes: ${query.text}")
        if (query.text.length > 2) {
            query.num = PAGE_SIZE
            this.viewModelScope.launch(Dispatchers.IO) {
                eventLive.postValue(Event.loading())
                val searchResults = repository.getRecipesByRequest(query)
                if (searchResults.results.isNotEmpty())
                    eventLive.postValue(Event.success(searchResults))
                else
                    eventLive.postValue(Event.empty())
            }
        }
    }

    fun loadMore() {
        if (query.text.isNotEmpty()
            && query.num >= PAGE_SIZE
            && (query.num + PAGE_SIZE) < eventLive.value!!.data?.totalResults ?: 0
        ) {
            this.viewModelScope.launch(Dispatchers.IO) {
                eventLive.postValue(Event.paging())
                query.num += PAGE_SIZE
                val searchResults = repository.getRecipesByRequest(query)
                if (searchResults.results.isNotEmpty())
                    eventLive.postValue(Event.success(searchResults))
                else
                    eventLive.postValue(Event.empty())
            }
        }
    }

    private fun updateQuery() {
        this.viewModelScope.launch(Dispatchers.IO) {
            repository.updateLastQuery(query)
        }
    }

    private fun getLastSearchQuery(): SearchQuery {
        return runBlocking {
            repository.getLastSearchQuery()
        }
    }

    fun deleteLastSearchQuery() {
        runBlocking {
            repository.deleteLastSearchQuery()
        }
    }

}