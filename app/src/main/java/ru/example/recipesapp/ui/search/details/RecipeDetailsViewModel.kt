package ru.example.recipesapp.ui.search.details

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import ru.example.recipesapp.data.DataRepository
import ru.example.recipesapp.data.network.Event
import ru.example.recipesapp.data.network.model.details.ResponseDetails
import ru.example.recipesapp.ui.BaseViewModel

class RecipeDetailsViewModel(private val repository: DataRepository) : BaseViewModel(), Observable  {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
    val liveData = MutableLiveData<Event<ResponseDetails>>()

    /*@Bindable
    val loading = MutableLiveData<Boolean>()*/
    val recipeName = MutableLiveData<String>("")
    val recipeImage = MutableLiveData<String>("")

    fun requestDetails(id: Long) {
        requestWithLiveData(liveData) {
            repository.getRecipeDetails(id)
        }
    }

    /*fun onLoading() {
        loading.value = true
    }

    fun onSuccess(data: ResponseDetails) {
        loading.value = false
        recipeName.value = data.title
        recipeImage.value = data.image
    }*/

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}