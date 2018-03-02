package com.telesoftas.core.storage.preferences

import com.telesoftas.core.storage.RxStorage
import io.reactivex.Completable
import io.reactivex.Single

class RxPreferencesStorage<T>(
        private val storage: PreferencesStorage<T>
) : RxStorage<T> {
    override fun put(item: T): Completable = Completable.fromAction { storage.put(item) }

    override fun get(): Single<T> = Single.fromCallable { storage.get() }

    override fun clear(): Completable = Completable.fromAction { storage.clear() }
}