package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class OrmLiteDatabaseTableModel<T>(private val clazz: Class<T>) : DatabaseTableModel {
    override fun createTable(connectionSource: ConnectionSource) {
        TableUtils.createTable(connectionSource, clazz)
    }

    override fun dropTable(connectionSource: ConnectionSource) {
        TableUtils.dropTable<T, Long>(connectionSource, clazz, true)
    }
}