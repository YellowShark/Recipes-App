package ru.example.recipesapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_search_query")
data class SearchQuery(
    @ColumnInfo(name = "query")
    var text: String = "",
    @ColumnInfo(name = "num")
    var num: Int = 0,
    @ColumnInfo(name = "sort_by")
    var sortBy: String = "popularity",
    @ColumnInfo(name = "sort_dir")
    var sortDir: String = "desc",
    @ColumnInfo(name = "cuisine")
    var cuisine: String = "",
    @ColumnInfo(name = "diet")
    var diet: String = "",
    @ColumnInfo(name = "type")
    var type: String = ""
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}