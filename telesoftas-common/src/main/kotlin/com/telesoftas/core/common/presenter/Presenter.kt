package com.telesoftas.core.common.presenter

interface Presenter<in V> {
    fun takeView(view: V)

    fun dropView()
}