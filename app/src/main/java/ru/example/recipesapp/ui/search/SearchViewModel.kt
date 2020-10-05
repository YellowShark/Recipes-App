package ru.example.recipesapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.example.recipesapp.data.DataRepository
import ru.example.recipesapp.data.network.model.Meal

class SearchViewModel(private val repository: DataRepository) : ViewModel() {
    val liveData = MutableLiveData<List<Meal>>()
    fun startSearchRecipe(request: String) {
        this.viewModelScope.launch(Dispatchers.IO) {
            repository.getListOfRequestedMeal(liveData, request)
        }
    }
}