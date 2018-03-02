package com.telesoftas.core.storage.preferences.manager

import android.content.SharedPreferences
import com.google.gson.Gson

class DefaultPreferenceManager(
        private val prefs: SharedPreferences,
        private val gson: Gson
) : PreferenceManager {
    @android.annotation.SuppressLint("CommitPrefEdits")
    private val prefsEditor: SharedPreferences.Editor = prefs.edit()

    override fun put(key: String, value: Boolean) {
        prefsEditor.putBoolean(key, value).apply()
    }

    override fun put(key: String, value: String) {
        prefsEditor.putString(key, value).apply()
    }

    override fun put(key: String, value: Int) {
        prefsEditor.putInt(key, value).apply()
    }

    override fun <T> put(key: String, value: T) {
        val serializedValue = gson.toJson(value)
        prefsEditor.putString(key, serializedValue).apply()
    }

    override fun put(key: String, value: Long) {
        prefsEditor.putLong(key, value).apply()
    }

    override fun getLong(key: String, defValue: Long): Long = prefs.getLong(key, defValue)

    override fun getString(key: String, defValue: String): String = prefs.getString(key, defValue)

    override fun getBoolean(key: String, defValue: Boolean): Boolean =
            prefs.getBoolean(key, defValue)

    override fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    override fun clear(key: String) {
        prefsEditor.remove(key).apply()
    }

    override fun <T> getSerializedObject(key: String, defValue: T, clazz: Class<T>): T {
        val serializedValue = prefs.getString(key, "")
        if (serializedValue.isEmpty()) {
            return defValue
        }
        return gson.fromJson(serializedValue, clazz)
    }
}