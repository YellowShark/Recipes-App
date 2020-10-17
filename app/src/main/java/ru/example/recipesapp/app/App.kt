package ru.example.recipesapp.app

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.example.recipesapp.app.di.networkModule
import ru.example.recipesapp.app.di.repositoryModule
import ru.example.recipesapp.app.di.viewModelsModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModelsModule, networkModule, repositoryModule))
    }
}