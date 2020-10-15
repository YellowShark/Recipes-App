package ru.example.recipesapp.domain


import ru.example.recipesapp.data.network.RecipesApi
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.data.network.model.search.ResponseSearch

class DataRepository(private val api: RecipesApi) {

    suspend fun getListOfRequestedMeal(
        request: String,
        num: Int,
        sortBy: String,
        sortDir: String,
        cuisine: String,
        diet: String,
        type: String
    ): ResponseSearch {
        return api.getListOfRecipes(request, num, sortBy, sortDir, cuisine, diet, type)
    }

    suspend fun getRecipeDetails(id: Long): ResponseDetails = api.getRecipeDetails(id)
}