package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.dao.Dao
import com.telesoftas.core.storage.database.DatabaseStorage

class OrmLiteDatabaseStorage<T>(
        databaseHelper: DefaultDatabaseHelper,
        clazz: Class<T>
) : DatabaseStorage<T> {
    private val dao: Dao<T, Int> = databaseHelper.getDao(clazz)
    override suspend fun putItem(item: T) {
        dao.createIfNotExists(item)
    }

    override suspend fun putItems(items: List<T>) {
        items.forEach { item -> dao.createIfNotExists(item) }
    }

    override suspend fun getItems(): List<T> {
        return dao.queryForAll()
    }

    override suspend fun getItemById(id: Int): T {
        return dao.queryForId(id)
    }

    override suspend fun updateItem(item: T) {
        dao.createOrUpdate(item)
    }

    override suspend fun updateItems(items: List<T>) {
        items.forEach { item -> dao.createOrUpdate(item) }
    }

    override suspend fun deleteAll() {
        dao.deleteBuilder().delete()
    }
}