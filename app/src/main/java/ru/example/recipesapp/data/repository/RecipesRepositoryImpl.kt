package ru.example.recipesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.example.recipesapp.data.db.SearchDao
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery
import ru.example.recipesapp.data.network.RecipesDataSource
import ru.example.recipesapp.data.network.details.response.Details

class RecipesRepositoryImpl(
    private val recipesDataSource: RecipesDataSource,
    private val searchDao: SearchDao
) : RecipesRepository {

    private val _liveDetails = MutableLiveData<Details>()
    override val liveDetails: LiveData<Details>
        get() = _liveDetails

    init {
        recipesDataSource.apply {
            downloadedSearchResults.observeForever { results ->
                if (results != null) {
                    GlobalScope.launch(Dispatchers.IO) {
                        searchDao.upsert(results)
                    }
                }
            }
            downloadedDetails.observeForever { details ->
                if (details != null) {
                    _liveDetails.value = details
                }
            }
        }
    }

    override suspend fun requestDetails(id: Long) {
        withContext(Dispatchers.IO) {
            recipesDataSource.fetchDetails(id)
            delay(200)
        }
    }

    override suspend fun getRecipesByRequest(query: SearchQuery): CurrentSearchResults {
        return withContext(Dispatchers.IO) {
            initSearchData(query)
            return@withContext searchDao.getLastSearchResults() ?: emptyResult()
        }
    }

    override suspend fun getLastSearchQuery(): SearchQuery {
        return withContext(Dispatchers.IO) {
            return@withContext searchDao.getLastQuery() ?: emptySearchQuery()
        }
    }

    override suspend fun deleteLastSearchQuery() {
        searchDao.deleteLastQuery()
    }

    private fun emptySearchQuery() = SearchQuery()

    private suspend fun initSearchData(newQuery: SearchQuery) {
        val lastSearchQuery = searchDao.getLastQuery()
        if (lastSearchQuery!!.text.isEmpty() || lastSearchQuery != newQuery) {
            updateLastQuery(newQuery)
            recipesDataSource.fetchRecipes(newQuery)
            delay(200)
        }
    }

    override suspend fun updateLastQuery(newQuery: SearchQuery) {
        searchDao.rememberLastQuery(newQuery)
    }

    private fun emptyResult() = CurrentSearchResults()

}