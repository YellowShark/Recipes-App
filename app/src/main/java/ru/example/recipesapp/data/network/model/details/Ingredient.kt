package ru.example.recipesapp.data.network.model.details

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("image")
    val image: String
)