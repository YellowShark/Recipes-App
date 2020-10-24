package ru.example.recipesapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.example.recipesapp.data.network.search.response.Recipe

@Entity(tableName = "current_search_results")
data class CurrentSearchResults(
    @SerializedName("number")
    @ColumnInfo(name = "number")
    var number: Long = 0,
    @SerializedName("results")
    @ColumnInfo(name = "results")
    var results: List<Recipe> = emptyList(),
    @SerializedName("totalResults")
    @ColumnInfo(name = "total_results")
    var totalResults: Long = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}