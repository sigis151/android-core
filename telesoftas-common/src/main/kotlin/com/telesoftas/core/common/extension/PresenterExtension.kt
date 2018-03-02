package com.telesoftas.core.common.extension

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.telesoftas.core.common.presenter.Presenter
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
fun <V> Presenter<V>.attach(view: V) {
    when (view) {
        is Activity -> attachToActivity(this as Presenter<Activity>, view)
        is Fragment -> attachToFragment(this as Presenter<Fragment>, view)
        is View -> attachToView(this as Presenter<View>, view)
    }
}

private fun <V : View> attachToView(presenter: Presenter<V>, rootView: V) {
    rootView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            if (rootView == view) {
                logAttach(presenter, rootView)
                presenter.takeView(rootView)
            }
        }

        override fun onViewDetachedFromWindow(view: View) {
            if (rootView == view) {
                logDetach(presenter, rootView)
                rootView.removeOnAttachStateChangeListener(this)
                presenter.dropView()
            }
        }
    })
}

private fun <V : Activity> attachToActivity(presenter: Presenter<V>, view: V) {
    val application = view.application
    application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (view == activity) {
                logAttach(presenter, view)
                presenter.takeView(view)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            if (view == activity) {
                logDetach(presenter, view)
                application.unregisterActivityLifecycleCallbacks(this)
                presenter.dropView()
            }
        }
    })
}

private fun <V : Fragment> attachToFragment(presenter: Presenter<V>, view: V) {
    val fragmentManager = view.fragmentManager
    fragmentManager?.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentCreated(
                        manager: FragmentManager,
                        fragment: Fragment,
                        savedInstanceState: Bundle?
                ) {
                    if (view == fragment) {
                        logAttach(presenter, view)
                        presenter.takeView(view)
                    }
                }

                override fun onFragmentDestroyed(manager: FragmentManager, fragment: Fragment) {
                    if (view == fragment) {
                        logDetach(presenter, view)
                        manager.unregisterFragmentLifecycleCallbacks(this)
                        presenter.dropView()
                    }
                }
            }, false)
}

private fun <V> logAttach(presenter: Presenter<V>, view: V) {
    Timber.d("Attaching $view to ${presenter.javaClass.simpleName}")
}

private fun <V> logDetach(presenter: Presenter<V>, view: V) {
    Timber.d("Detaching $view from ${presenter.javaClass.simpleName}")
}

internal interface SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
}