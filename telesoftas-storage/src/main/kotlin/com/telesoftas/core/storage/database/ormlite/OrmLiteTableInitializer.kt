package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class OrmLiteTableInitializer(private val clazz: Class<Any>) : TableInitializer {
    override fun createTable(connectionSource: ConnectionSource) {
        TableUtils.createTable(connectionSource, clazz)
    }
}