package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Single
import java.sql.SQLException

interface DatabaseHelper {
    fun <T> execute(action: () -> T?): Single<T>

    fun execute(action: () -> Unit): Completable

    @Throws(SQLException::class)
    fun <D : Dao<T, *>, T> getDao(clazz: Class<T>): D

    class NullEntityException : SQLException("Your query returned null")
}