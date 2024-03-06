package com.dicoding.asclepius.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun ImageView.setImage(url: String?){
    Glide.with(this.rootView).load(url).apply(RequestOptions())
        .into(this)
}

fun ImageView.setImage(byte: ByteArray?){
    Glide.with(this.rootView).load(byte).apply(RequestOptions())
        .into(this)
}