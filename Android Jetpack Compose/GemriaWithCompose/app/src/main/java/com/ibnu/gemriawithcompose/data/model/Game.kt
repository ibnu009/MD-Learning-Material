package com.ibnu.gemriawithcompose.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var developerName: String = "",
    var releaseDate: String = "",
    var posterImage: Int = 0,
    var backgroundImage: Int = 0,
    var categories: String = "",
    var rating: Double = 0.0,
    var totalReviews: Int = 0,
    var price: Int = 0,
    var link: String = "",
    @ColumnInfo(name = "is_added_to_cart")
    var isAddedToCart: Boolean = false
)