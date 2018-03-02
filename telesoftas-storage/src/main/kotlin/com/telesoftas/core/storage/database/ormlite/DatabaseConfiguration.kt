package com.telesoftas.core.storage.database.ormlite

import android.database.sqlite.SQLiteDatabase

data class DatabaseConfiguration(
        val databaseName: String = "main.db",
        val databaseVersion: Int = 1,
        val cursorFactory: SQLiteDatabase.CursorFactory? = null
)