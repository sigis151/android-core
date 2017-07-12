package com.telesoftas.core.common.extension

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks
import android.view.View
import android.view.View.OnAttachStateChangeListener
import com.nhaarman.mockito_kotlin.*
import com.telesoftas.core.common.presenter.Presenter
import org.junit.Before
import org.junit.Test

class PresenterExtensionKtTest {
    private val activityPresenter = mock<Presenter<Activity>>()
    private val fragmentPresenter = mock<Presenter<Fragment>>()
    private val viewPresenter = mock<Presenter<View>>()
    private val activityLifecycleCaptor = argumentCaptor<ActivityLifecycleCallbacks>()
    private val fragmentLifecycleCaptor = argumentCaptor<FragmentLifecycleCallbacks>()
    private val viewLifecycleCaptor = argumentCaptor<OnAttachStateChangeListener>()
    private val fragmentManager = mock<FragmentManager>()
    private val application = mock<Application>()
    private val activity = mock<Activity>()
    private val fragment = mock<Fragment>()
    private val bundle = mock<Bundle>()
    private val view = mock<View>()

    @Before
    fun setUp() {
        mockActivityLifecycle()
        mockFragmentLifecycle()
        mockViewLifecycle()
    }

    private fun mockActivityLifecycle() {
        given(activity.application).willReturn(application)
        given(application.registerActivityLifecycleCallbacks(activityLifecycleCaptor.capture()))
                .will { }
    }

    private fun mockFragmentLifecycle() {
        given(fragment.fragmentManager).willReturn(fragmentManager)
        given(fragmentManager.registerFragmentLifecycleCallbacks(
                fragmentLifecycleCaptor.capture(), any())).will { }
    }

    private fun mockViewLifecycle() {
        given(view.addOnAttachStateChangeListener(viewLifecycleCaptor.capture())).will { }
    }

    @Test
    fun onActivityCreated_attachToActivity_callsPresenterTakeView() {
        val lifecycleCallbacks = attachForActivityLifecycle()

        lifecycleCallbacks.onActivityCreated(activity, bundle)

        verify(activityPresenter).takeView(activity)
        verify(activityPresenter, never()).dropView()
    }

    @Test
    fun onActivityCreated_differentInstance_attachToActivity_neverCallsPresenterTakeView() {
        val lifecycleCallbacks = attachForActivityLifecycle()

        lifecycleCallbacks.onActivityCreated(mock(), bundle)

        verify(activityPresenter, never()).takeView(activity)
        verify(activityPresenter, never()).dropView()
    }

    @Test
    fun onActivityDestroyed_attachToActivity_callsPresenterDropView() {
        val lifecycleCallbacks = attachForActivityLifecycle()

        lifecycleCallbacks.onActivityDestroyed(activity)

        verify(activityPresenter, never()).takeView(activity)
        verify(activityPresenter).dropView()
    }

    @Test
    fun onActivityDestroyed_differentInstance_attachToActivity_neverCallsPresenterDropView() {
        val lifecycleCallbacks = attachForActivityLifecycle()

        lifecycleCallbacks.onActivityDestroyed(mock())

        verify(activityPresenter, never()).takeView(activity)
        verify(activityPresenter, never()).dropView()
    }

    @Test
    fun onFragmentCreated_attachToFragment_callsPresenterTakeView() {
        val lifecycleCallbacks = attachForFragmentLifecycle()

        lifecycleCallbacks.onFragmentCreated(fragmentManager, fragment, bundle)

        verify(fragmentPresenter).takeView(fragment)
        verify(fragmentPresenter, never()).dropView()
    }

    @Test
    fun onFragmentCreated_differentInstance_attachToFragment_neverCallsPresenterTakeView() {
        val lifecycleCallbacks = attachForFragmentLifecycle()

        lifecycleCallbacks.onFragmentCreated(fragmentManager, mock(), bundle)

        verify(fragmentPresenter, never()).takeView(fragment)
        verify(fragmentPresenter, never()).dropView()
    }

    @Test
    fun onFragmentDestroyed_attachToFragment_callsPresenterDropView() {
        val lifecycleCallbacks = attachForFragmentLifecycle()

        lifecycleCallbacks.onFragmentDestroyed(fragmentManager, fragment)

        verify(fragmentPresenter, never()).takeView(fragment)
        verify(fragmentPresenter).dropView()
    }

    @Test
    fun onFragmentDestroyed_differentInstance_attachToActivity_neverCallsPresenterDropView() {
        val lifecycleCallbacks = attachForFragmentLifecycle()

        lifecycleCallbacks.onFragmentDestroyed(fragmentManager, mock())

        verify(fragmentPresenter, never()).takeView(fragment)
        verify(fragmentPresenter, never()).dropView()
    }

    @Test
    fun onViewAttached_attachToView_callsPresenterTakeView() {
        val lifecycleCallbacks = attachForViewLifecycle()

        lifecycleCallbacks.onViewAttachedToWindow(view)

        verify(viewPresenter).takeView(view)
        verify(viewPresenter, never()).dropView()
    }

    @Test
    fun onViewAttached_differentInstance_attachToView_neverCallsPresenterTakeView() {
        val lifecycleCallbacks = attachForViewLifecycle()

        lifecycleCallbacks.onViewAttachedToWindow(mock())

        verify(viewPresenter, never()).takeView(view)
        verify(viewPresenter, never()).dropView()
    }

    @Test
    fun onViewDetached_attachToView_callsPresenterDropView() {
        val lifecycleCallbacks = attachForViewLifecycle()

        lifecycleCallbacks.onViewDetachedFromWindow(view)

        verify(viewPresenter, never()).takeView(view)
        verify(viewPresenter).dropView()
    }

    @Test
    fun onViewDetached_differentInstance_attachToView_neverCallsPresenterDropView() {
        val lifecycleCallbacks = attachForViewLifecycle()

        lifecycleCallbacks.onViewDetachedFromWindow(mock())

        verify(viewPresenter, never()).takeView(view)
        verify(viewPresenter, never()).dropView()
    }

    private fun attachForActivityLifecycle(): ActivityLifecycleCallbacks {
        activityPresenter.attach(activity)
        return activityLifecycleCaptor.firstValue
    }

    private fun attachForFragmentLifecycle(): FragmentLifecycleCallbacks {
        fragmentPresenter.attach(fragment)
        return fragmentLifecycleCaptor.firstValue
    }

    private fun attachForViewLifecycle(): OnAttachStateChangeListener {
        viewPresenter.attach(view)
        return viewLifecycleCaptor.firstValue
    }
}