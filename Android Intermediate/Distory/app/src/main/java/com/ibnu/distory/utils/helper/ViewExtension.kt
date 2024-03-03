package com.ibnu.distory.utils.helper

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.ibnu.distory.utils.constant.AnimationConstants.POP_ANIMATION

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.setImageUrl(url: String?) {
    Glide.with(this.rootView).load(url).apply(RequestOptions())
        .into(this)
}

infix fun View.popClick(click: () -> Unit) {
    setOnClickListener {
        it.popTap()
        Handler(Looper.getMainLooper()).postDelayed({
            click()
        }, POP_ANIMATION)
    }
}

fun EditText.showError(message: String) {
    error = message
    requestFocus()
}

fun ShimmerFrameLayout.showAndStart() {
    this.startShimmer()
    this.showShimmer(true)
    this.show()
}

fun ShimmerFrameLayout.hideAndStop() {
    this.stopShimmer()
    this.showShimmer(false)
    this.gone()
}

fun View.showSnackBar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.setAlphaAnimation(animationSpeed: Long): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, View.ALPHA, 1f)
        .setDuration(animationSpeed)
}

fun View.popTap() {
    this.visibility = View.VISIBLE
    this.alpha = 1.0f

    this.pivotX = (this.width / 2).toFloat()
    this.pivotY = (this.height / 2).toFloat()

    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", 0.7f)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", 0.7f)

    scaleDownX.duration = 100
    scaleDownY.duration = 100

    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f)

    scaleUpX.duration = 100
    scaleUpY.duration = 100

    val scaleDown = AnimatorSet()
    scaleDown.play(scaleDownX).with(scaleDownY)
    scaleDown.start()

    val scaleUp = AnimatorSet()
    scaleUp.play(scaleUpX).with(scaleUpY).after(scaleDown)
    scaleUp.start()
}

