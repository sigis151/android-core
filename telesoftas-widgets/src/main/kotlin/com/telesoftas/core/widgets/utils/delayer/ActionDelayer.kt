package com.telesoftas.core.widgets.utils.delayer;

import android.os.Handler

class ActionDelayer(
        private val delayHandler: Handler,
        private var delayTime: Long
) {
    private var running: Boolean = false

    fun delay(action: () -> Unit) {
        if (!running) {
            invokeDelayedAction(delayTime, action)
            running = true
        }
    }

    private fun invokeDelayedAction(delayTime: Long, action: () -> Unit) {
        if (delayTime > 0) {
            delayHandler.postDelayed(action, delayTime)
        } else {
            action.invoke()
        }
    }

    fun cancelDelaysWithAction(action: () -> Unit) {
        delayHandler.removeCallbacksAndMessages(null)
        running = false
        action.invoke()
    }

    fun setDelay(delayTime: Long) {
        this.delayTime = delayTime
    }
}