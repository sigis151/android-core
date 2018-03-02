package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.dao.Dao
import com.telesoftas.core.storage.database.RxDatabaseStorage
import io.reactivex.Completable
import io.reactivex.Single

class RxOrmLiteDatabaseStorage<T>(
        private val databaseHelper: DefaultDatabaseHelper,
        clazz: Class<T>
) : RxDatabaseStorage<T> {
    private val dao: Dao<T, Int> = databaseHelper.getDao(clazz)

    override fun putItem(item: T): Single<T> = execute {
        dao.createIfNotExists(item)
        item
    }

    override fun putItems(items: List<T>): Single<List<T>> = execute {
        items.forEach { item -> dao.createIfNotExists(item) }
        items
    }

    override fun getItems(): Single<List<T>> = execute {
        dao.queryForAll()
    }

    override fun getItemById(id: Int): Single<T> = execute {
        dao.queryForId(id)
    }

    override fun updateItem(item: T): Single<T> = execute {
        dao.createOrUpdate(item)
        item
    }

    override fun updateItems(items: List<T>): Single<List<T>> = execute {
        items.forEach { item -> dao.createOrUpdate(item) }
        items
    }

    override fun deleteAll(): Completable = executeAction {
        dao.deleteBuilder().delete()
    }

    private fun <T> execute(callable: () -> T): Single<T> {
        return databaseHelper.execute(callable)
    }

    private fun executeAction(callable: () -> Unit): Completable {
        return databaseHelper.execute(callable)
    }
}