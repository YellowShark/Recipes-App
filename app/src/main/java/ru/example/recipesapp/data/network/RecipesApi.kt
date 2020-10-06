package ru.example.recipesapp.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.example.recipesapp.data.network.model.SearchResponse

interface RecipesApi {
    @GET("/recipes/complexSearch")
    fun getListOfRecipes(
        @Query("query") mealName: String,
        @Query("number") num: Int
    ) : Call<SearchResponse>
}