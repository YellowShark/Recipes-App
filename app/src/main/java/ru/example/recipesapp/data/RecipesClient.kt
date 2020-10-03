package ru.example.recipesapp.data

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecipesClient {

    companion object {
        val BASE_URL = "https://webknox-recipes.p.rapidapi.com/"
        val API_KEY = "bded0e4356msha8326dd418def83p1b8ee9jsn2087776a706d"

        fun getApi(): RecipesApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("x-rapidapi-key", API_KEY)
                    .build()

                chain.proceed(request) }
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(RecipesApi::class.java)
        }
    }
}