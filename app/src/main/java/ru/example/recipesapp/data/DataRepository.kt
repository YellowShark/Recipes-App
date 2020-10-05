package ru.example.recipesapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import ru.example.recipesapp.data.network.RecipesApi
import ru.example.recipesapp.data.network.model.Meal
import ru.example.recipesapp.data.network.model.SearchResponse
import javax.security.auth.callback.Callback

class DataRepository(val api: RecipesApi) {
    suspend fun getListOfRequestedMeal(liveData: MutableLiveData<List<Meal>>, request: String) {
        api.getListOfRecipes(request, 10).enqueue(object : Callback,
            retrofit2.Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                liveData.postValue(response.body()?.results)
                Log.d("TAG", "onResponse:\n" +
                        "header: ${response.headers()}\n" +
                        "body: ${response.body().toString()}\n" +
                        "message: ${response.message()}")
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                t.printStackTrace()
                Log.d("TAG", "onFailure: " + "something went wrong")
            }

        })
    }

}