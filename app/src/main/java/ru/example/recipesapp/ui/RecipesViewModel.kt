package ru.example.recipesapp.ui

import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.example.recipesapp.data.Repository
import ru.example.recipesapp.data.model.Meal

class RecipesViewModel : ViewModel() {
    private val mealsList = listOf(Meal(123, "Borsch"))
    val liveData = MutableLiveData<List<Meal>>()
    private val repository: Repository = Repository()

    init {
        liveData.value = mealsList
    }

    fun callApi() = repository.callRecipesApi(liveData, "burger")
}