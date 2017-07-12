package com.telesoftas.core.common.presenter

open class BasePresenter<V> : Presenter<V> {
    protected open var view: V? = null

    override fun takeView(view: V) {
        this.view = view
    }

    override fun dropView() {
        view = null // NOPMD
    }

    protected fun hasView() = view != null

    protected fun onView(action: V.() -> Unit) {
        if (hasView()) {
            action.invoke(view!!)
        }
    }
}