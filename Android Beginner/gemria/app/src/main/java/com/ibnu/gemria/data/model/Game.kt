package com.ibnu.gemria.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
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
    ): Parcelable