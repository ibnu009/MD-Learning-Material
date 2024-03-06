package com.dicoding.asclepius.utils

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.IOException
import java.io.InputStream

fun Uri.uriToBase64(context: Context): String {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(this)
    val bytes = inputStream?.readBytes() ?: throw IOException("Cannot read image bytes")
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}