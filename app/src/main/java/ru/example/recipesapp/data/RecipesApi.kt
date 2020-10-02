package ru.example.recipesapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.example.recipesapp.data.model.Meal

interface RecipesApi {
    @GET("/recipes/search")
    fun getListOfRecipes(@Query("query") mealName: String) : Call<List<Meal>>
}