package ru.example.recipesapp.app.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.example.recipesapp.data.network.RecipesClient
import ru.example.recipesapp.domain.DataRepository
import ru.example.recipesapp.ui.search.details.RecipeDetailsViewModel
import ru.example.recipesapp.ui.search.main.SearchViewModel

val viewModelsModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}

val networkModule = module {
    single { RecipesClient.getApi() }
}

val repositoryModule = module {
    single { DataRepository(get()) }
}
