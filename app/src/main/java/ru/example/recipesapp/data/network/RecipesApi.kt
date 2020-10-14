package ru.example.recipesapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.data.network.model.search.ResponseSearch

interface RecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getListOfRecipes(
        @Query("query") mealName: String,
        @Query("number") num: Int = 10,
        @Query("sort") sortBy: String,
        @Query("sortDirection") sortDirection: String
    ) : ResponseSearch

    @GET("/recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Long
    ) : ResponseDetails
}