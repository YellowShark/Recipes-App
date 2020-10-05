package ru.example.recipesapp.ui

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.example.recipesapp.di.searchModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(searchModule))
    }
}