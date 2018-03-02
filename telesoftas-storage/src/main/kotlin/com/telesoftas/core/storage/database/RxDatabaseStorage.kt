package com.telesoftas.core.storage.database

import io.reactivex.Completable
import io.reactivex.Single

interface RxDatabaseStorage<T> {
    fun putItem(item: T): Single<T>

    fun putItems(items: List<T>): Single<List<T>>

    fun getItems(): Single<List<T>>

    fun getItemById(id: Int): Single<T>

    fun updateItem(item: T): Single<T>

    fun updateItems(items: List<T>): Single<List<T>>

    fun deleteAll(): Completable
}