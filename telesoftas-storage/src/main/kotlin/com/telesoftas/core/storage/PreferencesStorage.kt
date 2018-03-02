package com.telesoftas.core.storage

import com.telesoftas.core.storage.preferences.manager.PreferenceManager

open class PreferencesStorage<T>(
        private val preferenceManager: PreferenceManager,
        private val key: String,
        private val clazz: Class<T>,
        private val defaultValue: T
) : Storage<T> {
    override fun put(item: T) {
        preferenceManager.put(key, item)
    }

    override fun get(): T = preferenceManager.getSerializedObject(key, defaultValue, clazz)

    override fun clear() {
        preferenceManager.clear(key)
    }
}