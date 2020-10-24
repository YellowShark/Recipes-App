package ru.example.recipesapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.network.details.response.Details

interface RecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getListOfRecipes(
        @Query("query") mealName: String,
        @Query("number") num: Int = 10,
        @Query("sort") sortBy: String,
        @Query("sortDirection") sortDirection: String,
        @Query("cuisine") cuisine: String = "",
        @Query("diet") diet: String = "",
        @Query("type") mealType: String = ""
    ) : Response<CurrentSearchResults> 

    @GET("/recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Long
    ) : Response<Details>
}