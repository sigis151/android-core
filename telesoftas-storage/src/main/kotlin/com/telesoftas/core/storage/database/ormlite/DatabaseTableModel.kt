package com.telesoftas.core.storage.database.ormlite

import com.j256.ormlite.support.ConnectionSource

interface DatabaseTableModel {
    fun createTable(connectionSource: ConnectionSource)
}