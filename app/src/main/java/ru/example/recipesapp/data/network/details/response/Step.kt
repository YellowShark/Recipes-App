package ru.example.recipesapp.data.network.details.response

import com.google.gson.annotations.SerializedName

data class Step (
    @SerializedName("number")
    val number: String,
    @SerializedName("step")
    val step: String,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("equipment")
    val equipment: List<Any>
)