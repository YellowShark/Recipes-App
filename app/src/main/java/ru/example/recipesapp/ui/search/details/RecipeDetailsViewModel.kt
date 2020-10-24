package ru.example.recipesapp.ui.search.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.example.recipesapp.data.network.details.response.Details
import ru.example.recipesapp.data.repository.RecipesRepository
import ru.example.recipesapp.utils.Event

class RecipeDetailsViewModel(
    private val repository: RecipesRepository
) : ViewModel()  {

    init {
        repository.liveDetails.observeForever {
            if (it != null)
                eventLive.value = Event.success(it)
            else
                eventLive.value = Event.error(null)
        }
    }

    val eventLive = MutableLiveData<Event<Details>>(Event.default())

    fun requestDetails(id: Long) {
        this.viewModelScope.launch(Dispatchers.IO) {
            eventLive.postValue(Event.loading())
            repository.requestDetails(id)
            delay(500)
        }
    }
}