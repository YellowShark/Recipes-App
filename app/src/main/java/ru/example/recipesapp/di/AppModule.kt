package ru.example.recipesapp.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.example.recipesapp.data.DataRepository
import ru.example.recipesapp.data.network.RecipesClient
import ru.example.recipesapp.ui.search.SearchViewModel

val searchModule : Module = module {
    single { RecipesClient.getApi() }

    factory { DataRepository(get()) }

    viewModel { SearchViewModel(get()) }
}
