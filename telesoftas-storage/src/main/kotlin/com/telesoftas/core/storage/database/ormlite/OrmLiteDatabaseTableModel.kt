package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class OrmLiteDatabaseTableModel(private val clazz: Class<Any>) : DatabaseTableModel {
    override fun createTable(connectionSource: ConnectionSource) {
        TableUtils.createTable(connectionSource, clazz)
    }
}