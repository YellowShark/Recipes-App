package ru.example.recipesapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.example.recipesapp.data.db.entity.CurrentSearchResults
import ru.example.recipesapp.data.db.entity.SearchQuery

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipes: CurrentSearchResults)
    @Query("SELECT * FROM current_search_results")
    suspend fun getLastSearchResults(): CurrentSearchResults?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun rememberLastQuery(query: SearchQuery)
    @Query("SELECT * FROM last_search_query")
    suspend fun getLastQuery(): SearchQuery?
    @Query("DELETE FROM last_search_query")
    suspend fun deleteLastQuery()
}