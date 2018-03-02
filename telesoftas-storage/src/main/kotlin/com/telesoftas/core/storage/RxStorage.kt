package com.telesoftas.core.storage

import io.reactivex.Completable
import io.reactivex.Single

interface RxStorage<T> {
    fun put(item: T): Completable

    fun get(): Single<T>

    fun clear(): Completable
}