package ru.example.recipesapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.example.recipesapp.data.network.search.response.Recipe


object RecipeListConverters {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun recipesToString(recipes: List<Recipe>): String {
        return gson.toJson(recipes)
    }

    @TypeConverter
    @JvmStatic
    fun stringToRecipes(recipes: String): List<Recipe> {
        val listType = object : TypeToken<List<Recipe?>?>() {}.type

        return gson.fromJson(recipes, listType)
    }

}