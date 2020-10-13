package ru.example.recipesapp.ui.search.details

import androidx.lifecycle.MutableLiveData
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.domain.DataRepository
import ru.example.recipesapp.ui.BaseViewModel
import ru.example.recipesapp.utils.Event

class RecipeDetailsViewModel(private val repository: DataRepository) : BaseViewModel()  {

    val liveData = MutableLiveData<Event<ResponseDetails>>()

    val recipeName = MutableLiveData<String>("")
    val recipeImage = MutableLiveData<String>("")

    fun requestDetails(id: Long) {
        requestWithLiveData(liveData) {
            repository.getRecipeDetails(id)
        }
    }
}