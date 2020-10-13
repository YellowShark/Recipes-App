package ru.example.recipesapp.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.example.recipesapp.data.network.RecipesClient
import ru.example.recipesapp.domain.DataRepository
import ru.example.recipesapp.ui.search.details.RecipeDetailsViewModel
import ru.example.recipesapp.ui.search.main.SearchViewModel

val searchModule : Module = module {
    single { RecipesClient.getApi() }

    single { DataRepository(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { RecipeDetailsViewModel(get()) }
}
