package ru.example.recipesapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import ru.example.recipesapp.data.model.Meal
import javax.security.auth.callback.Callback

class Repository {
    fun callRecipesApi(liveData: MutableLiveData<List<Meal>>, request: String) {
        RecipesClient.getApi().getListOfRecipes(request).enqueue(object : Callback,
            retrofit2.Callback<List<Meal>> {
            override fun onResponse(call: Call<List<Meal>>, response: Response<List<Meal>>) {
                liveData.value = response.body()
                Log.d("TAG", "onResponse: ${response.body().toString()} ${response.message()}")
            }

            override fun onFailure(call: Call<List<Meal>>, t: Throwable) {
                t.printStackTrace()
                Log.d("TAG", "onFailure: " + "something went wrong")
            }

        })
    }

}