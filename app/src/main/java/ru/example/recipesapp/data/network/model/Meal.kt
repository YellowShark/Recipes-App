package ru.example.recipesapp.data.network.model

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("imageType") val imageType: String? = null,
    @SerializedName("calories") var calories: Long? = null,
    @SerializedName("carbs") val carbs: String? = null,
    @SerializedName("fat") val fat: String? = null,
    @SerializedName("protein") val protein: String? = null
)