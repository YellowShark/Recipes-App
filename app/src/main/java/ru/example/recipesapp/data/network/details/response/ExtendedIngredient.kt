package ru.example.recipesapp.data.network.details.response


import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("originalName")
    val originalName: String
)