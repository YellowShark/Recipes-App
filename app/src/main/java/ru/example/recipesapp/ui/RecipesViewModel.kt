package ru.example.recipesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.example.recipesapp.data.Repository
import ru.example.recipesapp.data.model.Meal

class RecipesViewModel : ViewModel() {

    val data : List<Meal>
            get() = Repository.getListOfRecipes()
}