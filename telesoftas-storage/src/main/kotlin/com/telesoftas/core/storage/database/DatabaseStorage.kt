package com.telesoftas.core.storage.database

interface DatabaseStorage<T> {
    suspend fun putItem(item: T)

    suspend fun putItems(items: List<T>)

    suspend fun getItems(): List<T>

    suspend fun getItemById(id: Int): T

    suspend fun updateItem(item: T)

    suspend fun updateItems(items: List<T>)

    suspend fun deleteAll()
}