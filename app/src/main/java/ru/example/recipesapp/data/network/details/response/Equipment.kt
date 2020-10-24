package ru.example.recipesapp.data.network.details.response

import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("image")
    val image: String
)