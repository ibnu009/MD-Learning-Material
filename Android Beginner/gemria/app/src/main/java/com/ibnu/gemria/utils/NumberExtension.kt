package com.ibnu.gemria.utils

import java.text.NumberFormat

fun Double.toRating(): String {
    if (this > 4.8) return "Perfect"
    if (this > 4.2 && this < 4.8) return "Very Positive"
    if (this > 3.5 && this < 4.2) return "Positive"
    if (this > 3.0 && this < 3.5) return "Decent"
    return "Bad"
}

fun Int.formatThousand(): String {
    return NumberFormat.getInstance().format(this)
}

fun Int.formatPriceThousand(): String {
    if (this == 0) return "Free-To-Play"
    return "Rp ${NumberFormat.getInstance().format(this)}"
}