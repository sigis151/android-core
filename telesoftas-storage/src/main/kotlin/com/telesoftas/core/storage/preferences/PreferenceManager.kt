package com.telesoftas.core.storage.preferences

interface PreferenceManager {
    fun put(key: String, value: Boolean)

    fun put(key: String, value: String)

    fun put(key: String, value: Int)

    fun put(key: String, value: Long)

    fun <T> put(key: String, value: T)

    fun getBoolean(key: String, defValue: Boolean): Boolean

    fun getString(key: String, defValue: String): String

    fun getInt(key: String, defValue: Int): Int

    fun getLong(key: String, defValue: Long): Long

    fun <T> getSerializedObject(key: String, defValue: T, clazz: Class<T>): T

    fun clear(key: String)
}