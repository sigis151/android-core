package com.telesoftas.core.common.extension

import android.app.Activity
import android.os.Bundle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test

class SimpleActivityLifecycleCallbacksTest {
    @Test
    fun invocations_neverInteractsWithParameters() {
        val activity = mock<Activity>()
        val bundle = mock<Bundle>()
        val callbacks = object : SimpleActivityLifecycleCallbacks {}

        callbacks.onActivityCreated(activity, bundle)
        callbacks.onActivityDestroyed(activity)
        callbacks.onActivityPaused(activity)
        callbacks.onActivityResumed(activity)
        callbacks.onActivitySaveInstanceState(activity, bundle)
        callbacks.onActivityStarted(activity)
        callbacks.onActivityStopped(activity)

        verifyZeroInteractions(activity)
        verifyZeroInteractions(bundle)
    }
}