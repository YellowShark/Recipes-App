package ru.example.recipesapp.data.network

import androidx.lifecycle.LiveData
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery
import ru.example.recipesapp.data.network.details.response.Details

interface RecipesDataSource {
    val downloadedSearchResults: LiveData<CurrentSearchResults?>
    val downloadedDetails: LiveData<Details?>

    suspend fun fetchRecipes(query: SearchQuery)

    suspend fun fetchDetails(id: Long)
}