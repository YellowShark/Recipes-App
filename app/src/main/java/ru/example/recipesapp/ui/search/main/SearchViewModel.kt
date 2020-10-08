package ru.example.recipesapp.ui.search.main

import androidx.lifecycle.MutableLiveData
import ru.example.recipesapp.data.DataRepository
import ru.example.recipesapp.data.network.Event
import ru.example.recipesapp.data.network.model.search.ResponseSearch
import ru.example.recipesapp.ui.BaseViewModel

class SearchViewModel(private val repository: DataRepository) : BaseViewModel() {
    val liveData = MutableLiveData<Event<ResponseSearch>>()

    fun startSearchRecipe(request: String) {
         requestWithLiveData(liveData) {
             repository.getListOfRequestedMeal(request)
         }
    }
}