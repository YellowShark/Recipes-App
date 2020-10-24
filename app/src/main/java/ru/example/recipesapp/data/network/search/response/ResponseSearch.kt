package ru.example.recipesapp.data.network.search.response

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("offset")
    val offset: Long? = null,
    @SerializedName("number")
    val number: Long = 0,
    @SerializedName("results")
    val results: List<Recipe> = emptyList(),
    @SerializedName("totalResults")
    val totalResults: Long = 0
)