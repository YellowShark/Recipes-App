package ru.example.recipesapp.app.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.example.recipesapp.data.db.RecipesDatabase
import ru.example.recipesapp.data.network.RecipesClient
import ru.example.recipesapp.data.network.RecipesDataSource
import ru.example.recipesapp.data.network.RecipesDataSourceImpl
import ru.example.recipesapp.data.repository.RecipesRepository
import ru.example.recipesapp.data.repository.RecipesRepositoryImpl
import ru.example.recipesapp.ui.search.details.RecipeDetailsViewModel
import ru.example.recipesapp.ui.search.main.SearchViewModel

val viewModelsModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}

val networkModule = module {
    single { RecipesClient.invoke() }
    single<RecipesDataSource> { RecipesDataSourceImpl(get()) }
}

val databaseModule = module {
    single { RecipesDatabase.invoke(androidApplication()).currentSearchDao() }

}

val repositoryModule = module {
    single<RecipesRepository> { RecipesRepositoryImpl(get(), get()) }
}
