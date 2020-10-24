package ru.example.recipesapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery
import ru.example.recipesapp.data.network.details.response.Details
import java.io.IOException

class RecipesDataSourceImpl(
    private val api: RecipesApi,
) : RecipesDataSource {

    private val _downloadedSearchResults = MutableLiveData<CurrentSearchResults?>()
    override val downloadedSearchResults: LiveData<CurrentSearchResults?>
        get() {
            return _downloadedSearchResults
        }

    override suspend fun fetchRecipes(query: SearchQuery) {
        try {
            val response: Response<CurrentSearchResults>
            with(query) {
                response = api.getListOfRecipes(text, num, sortBy, sortDir, cuisine, diet, type)
            }
            Log.d("TAG", "fetchDetails:\nCode: ${response.code()}\nMsg: ${response.message()} ")
            if (response.isSuccessful) {
                _downloadedSearchResults.postValue(response.body())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            _downloadedSearchResults.postValue(null)
        }
    }

    private val _downloadedDetails = MutableLiveData<Details?>()
    override val downloadedDetails: LiveData<Details?>
        get() = _downloadedDetails

    override suspend fun fetchDetails(id: Long) {
        try {
            val response = api.getRecipeDetails(id)
            if (response.isSuccessful)
                _downloadedDetails.postValue(response.body())
        } catch (e: IOException) {
            e.printStackTrace()
            _downloadedDetails.postValue(null)
        }
    }
}