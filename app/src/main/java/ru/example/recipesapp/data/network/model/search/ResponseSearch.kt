package ru.example.recipesapp.data.network.model.search

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("offset")
    val offset: Long? = null,
    @SerializedName("number")
    val number: Long? = null,
    @SerializedName("results")
    val results: List<Meal>? = null,
    @SerializedName("totalResults")
    val totalResults: Long = 0
)