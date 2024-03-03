package com.ibnu.distory.utils.helper

import android.content.Context
import com.ibnu.distory.R
import java.text.SimpleDateFormat
import java.util.*

fun String.isEmailCorrect(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isFullNameCorrect(): Boolean {
    return !Regex("[^a-zA-Z ]").containsMatchIn(this)
}

fun String.getTimeAgo(context: Context): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(this)
    val now = Date()
    val seconds = ((now.time - (date?.time ?: Date().time)) / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    return when {
        days > 0 -> context.getString(R.string.label_day, days)
        hours > 0 -> context.getString(R.string.label_hour, hours)
        minutes > 0 -> context.getString(R.string.label_minutes, minutes)
        else -> context.getString(R.string.label_seconds, seconds)
    }
}