package ru.example.recipesapp.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.example.recipesapp.data.DataRepository
import ru.example.recipesapp.data.Event
import ru.example.recipesapp.data.network.model.ResponseSearch

class SearchViewModel(private val repository: DataRepository) : ViewModel() {
    val liveData = MutableLiveData<Event<ResponseSearch>>()

    fun startSearchRecipe(request: String) {
         requestWithLiveData(liveData) {
             repository.getListOfRequestedMeal(request)
         }
    }

    private fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> T
    ) {

        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                Log.d("TAG", "requestWithLiveData: $response")
                if (response != null) {
                    liveData.postValue(Event.success(response))
                } else {
                    liveData.postValue(Event.error(null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(null))
            }
        }
    }
}