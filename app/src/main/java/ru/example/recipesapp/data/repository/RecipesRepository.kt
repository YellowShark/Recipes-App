package ru.example.recipesapp.data.repository

import androidx.lifecycle.LiveData
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery
import ru.example.recipesapp.data.network.details.response.Details

interface RecipesRepository {

    val liveDetails: LiveData<Details>

    suspend fun requestDetails(id: Long)

    suspend fun getRecipesByRequest(query: SearchQuery): CurrentSearchResults

    suspend fun getLastSearchQuery(): SearchQuery

    suspend fun deleteLastSearchQuery()

    suspend fun updateLastQuery(newQuery: SearchQuery)

}