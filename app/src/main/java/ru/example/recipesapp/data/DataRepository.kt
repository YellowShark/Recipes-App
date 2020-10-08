package ru.example.recipesapp.data


import ru.example.recipesapp.data.network.RecipesApi
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.data.network.model.search.ResponseSearch

class DataRepository(private val api: RecipesApi) {

    suspend fun getListOfRequestedMeal(request: String) : ResponseSearch = api.getListOfRecipes(request, num = 10)

    suspend fun getRecipeDetails(id: Long) : ResponseDetails = api.getRecipeDetails(id)
}