package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.support.ConnectionSource

interface TableInitializer {
    fun createTable(connectionSource: ConnectionSource)
}