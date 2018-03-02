package com.telesoftas.core.storage.preferences

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.telesoftas.core.storage.preferences.manager.PreferenceManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PreferencesStorageTest {
    private lateinit var storage: PreferencesStorage<Any>
    private val preferenceManager = mock<PreferenceManager>()
    private val key = "key.default"
    private val clazz = Any::class.java
    private val item = Any()
    private val default = Any()

    @Before
    fun setUp() {
        storage = PreferencesStorage(preferenceManager, key, clazz, default)
    }

    @Test
    fun put_putsItemToPreferencesWithoutErrors() {
        storage.put(item)

        verify(preferenceManager).put(key, item)
    }

    @Test
    fun get_retrievesItemFromPreferencesWithoutErrors() {
        given(preferenceManager.getSerializedObject(key, default, clazz)).willReturn(item)

        val actual = storage.get()

        assertEquals("Incorrect item returned", item, actual)
    }

    @Test
    fun clear_valueIsClearedFromPreferencesWithoutErrors() {
        storage.clear()

        verify(preferenceManager).clear(key)
    }
}