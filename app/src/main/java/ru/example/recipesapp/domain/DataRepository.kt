package ru.example.recipesapp.domain


import android.util.Log
import ru.example.recipesapp.data.network.RecipesApi
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.data.network.model.search.ResponseSearch

class DataRepository(private val api: RecipesApi) {

    suspend fun getListOfRequestedMeal(
        request: String, num: Int, sortBy: String, sortDir: String
    ) : ResponseSearch {
        Log.d("TAG", "getListOfRequestedMeal: $request, $num, $sortBy, $sortDir")
        return api.getListOfRecipes(request, num, sortBy, sortDir)
    }

    suspend fun getRecipeDetails(id: Long) : ResponseDetails = api.getRecipeDetails(id)
}