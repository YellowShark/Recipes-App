package ru.example.recipesapp.ui.search.main

import androidx.lifecycle.MutableLiveData
import ru.example.recipesapp.data.network.model.search.ResponseSearch
import ru.example.recipesapp.domain.DataRepository
import ru.example.recipesapp.ui.BaseViewModel
import ru.example.recipesapp.utils.Event

class SearchViewModel(private val repository: DataRepository) : BaseViewModel() {
    val liveData = MutableLiveData<Event<ResponseSearch>>(Event.loading())
    var sortBy = "popularity"
    var sortDir = "asc"

    fun startSearchRecipe(request: String, num: Int) {
         requestWithLiveData(liveData) {
             repository.getListOfRequestedMeal(request, num, sortBy, sortDir)
         }
    }
}