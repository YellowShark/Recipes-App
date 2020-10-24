package ru.example.recipesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery

@Database(entities = [CurrentSearchResults::class, SearchQuery::class], version = 3)
@TypeConverters(RecipeListConverters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun currentSearchDao(): SearchDao

    companion object {
        @Volatile
        private var INSTANCE: RecipesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext, RecipesDatabase::class.java, "recipes.db"
                )
                .fallbackToDestructiveMigration()
                .build()
    }
}