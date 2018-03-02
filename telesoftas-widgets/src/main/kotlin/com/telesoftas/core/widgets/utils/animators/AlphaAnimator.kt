package com.telesoftas.core.widgets.utils.animators;

import android.animation.ValueAnimator
import android.view.View

class AlphaAnimator {
    private var view: View? = null
    private val animator: ValueAnimator
        get() {
            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.addUpdateListener { valueAnimator ->
                val animatedValue = valueAnimator.animatedValue as Float
                view?.alpha = animatedValue
            }
            return animator
        }

    fun showProgress() {
        if (isPaused()) {
            view?.alpha = 0f
            view?.visibility = View.VISIBLE
            animator.start()
        }
    }

    fun hideProgress() {
        animator.cancel()
        view?.visibility = View.GONE
        view?.alpha = 0f
    }

    fun setDuration(duration: Long) {
        animator.duration = duration
    }

    fun setTarget(view: View) {
        this.view = view
    }

    private fun isPaused() = !animator.isStarted || !animator.isRunning
}