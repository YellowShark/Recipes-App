package ru.example.recipesapp.data.model

import com.google.gson.annotations.SerializedName


data class Meal(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("imageUrls")
    var imageUrls: List<String> = listOf(),

    @SerializedName("image")
    var image: String = "",
)