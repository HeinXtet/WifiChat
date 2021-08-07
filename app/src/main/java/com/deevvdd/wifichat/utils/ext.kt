package com.deevvdd.wifichat.utils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.lifecycle.MutableLiveData

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun View.fading() {
    val animator = ObjectAnimator.ofFloat(0f, 1f)
    animator.duration = 1000
    animator.interpolator = AccelerateInterpolator()
    animator.addUpdateListener {
        val value = it.animatedValue as Float
        this.alpha = value
    }
    animator.start()
}

fun View.bounceUp(delay: Long = 0L) {
    val yValue = 400
    this.translationY = yValue.toFloat()
    val animator = ObjectAnimator.ofFloat(200f, 0f)
    animator.duration = 500
    animator.interpolator = BounceInterpolator()
    animator.startDelay = delay
    animator.addUpdateListener {
        val value = it.animatedValue as Float
        this.translationY = value
    }
    animator.start()
}
