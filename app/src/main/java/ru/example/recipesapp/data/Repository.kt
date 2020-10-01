package ru.example.recipesapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.example.recipesapp.data.model.Meal

object Repository {

    private val mealsList: List<Meal> = listOf(Meal("Borsch"), Meal("Okroshka"))
    private val meals = MutableLiveData<List<Meal>>()

    init {
        meals.value = mealsList
    }

    fun getListOfRecipes(): List<Meal> = mealsList

}