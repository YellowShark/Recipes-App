package ru.example.recipesapp.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.example.recipesapp.data.network.model.ResponseSearch

interface RecipesApi {
    @GET("/recipes/complexSearch")
    suspend fun getListOfRecipes(
        @Query("query") mealName: String,
        @Query("number") num: Int
    ) :ResponseSearch
}