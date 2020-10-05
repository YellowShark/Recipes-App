package ru.example.recipesapp.data.network.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("offset") val offset: Long? = null,
    @SerializedName("number") val number: Long? = null,
    @SerializedName("results") val results: List<Meal>? = null,
    @SerializedName("totalResults") val totalResults: Long? = null
)