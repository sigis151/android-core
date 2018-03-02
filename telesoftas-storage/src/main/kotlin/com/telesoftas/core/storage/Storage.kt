package com.telesoftas.core.storage

interface Storage<T> {
    fun put(item: T)

    fun get(): T

    fun clear()
}