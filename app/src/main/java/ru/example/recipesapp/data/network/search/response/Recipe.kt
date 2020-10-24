package ru.example.recipesapp.data.network.search.response

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("imageType") val imageType: String,
    @SerializedName("calories") var calories: Long,
    @SerializedName("carbs") val carbs: String,
    @SerializedName("fat") val fat: String,
    @SerializedName("protein") val protein: String
)