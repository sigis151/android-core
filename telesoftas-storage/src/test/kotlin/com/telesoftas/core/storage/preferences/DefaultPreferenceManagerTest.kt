package com.telesoftas.core.storage.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*

class DefaultPreferenceManagerTest {
    private lateinit var prefsManager: DefaultPreferenceManager
    private val editor = mock<SharedPreferences.Editor>()
    private val preferences = mock<SharedPreferences>()
    private val gson = Gson()

    @Before
    fun setUp() {
        mockPreferences()
        prefsManager = DefaultPreferenceManager(preferences, gson)
    }

    @SuppressLint("CommitPrefEdits")
    private fun mockPreferences() {
        whenever(preferences.edit()).thenReturn(editor)
        whenever(editor.putInt(anyString(), anyInt())).thenReturn(editor)
        whenever(editor.putLong(anyString(), anyLong())).thenReturn(editor)
        whenever(editor.putString(anyString(), anyString())).thenReturn(editor)
        whenever(editor.putBoolean(anyString(), anyBoolean())).thenReturn(editor)
        whenever(editor.remove(anyString())).thenReturn(editor)
    }

    @Test
    fun testPutBoolean_appliesPutValueToEditor() {
        prefsManager.put(TEST_KEY_GENERIC, TEST_BOOLEAN)

        verify(editor).putBoolean(TEST_KEY_GENERIC, TEST_BOOLEAN)
        verify(editor).apply()
    }

    @Test
    fun testPutString_appliesPutValueToEditor() {
        prefsManager.put(TEST_KEY_GENERIC, TEST_STRING)

        verify(editor).putString(TEST_KEY_GENERIC, TEST_STRING)
        verify(editor).apply()
    }

    @Test
    fun testPutInt_appliesPutValueToEditor() {
        prefsManager.put(TEST_KEY_GENERIC, TEST_INT)

        verify(editor).putInt(TEST_KEY_GENERIC, TEST_INT)
        verify(editor).apply()
    }

    @Test
    fun testPutLong_appliesPutValueToEditor() {
        prefsManager.put(TEST_KEY_GENERIC, TEST_LONG)

        verify(editor).putLong(TEST_KEY_GENERIC, TEST_LONG)
        verify(editor).apply()
    }

    @Test
    fun testPutTypedObject_appliesSerializedValueToEditor() {
        prefsManager.put(TEST_KEY_GENERIC, TEST_OBJECT)

        verify(editor).putString(TEST_KEY_GENERIC, gson.toJson(TEST_OBJECT))
        verify(editor).apply()
    }

    @Test
    fun testGetString_returnsDefaultValueWhenKeyNotFound() {
        whenever(preferences.getString(TEST_KEY_GENERIC, TEST_DEF_STRING))
                .thenReturn(TEST_DEF_STRING)

        val value = prefsManager.getString(TEST_KEY_GENERIC, TEST_DEF_STRING)

        assertEquals("Incorrect String value", TEST_DEF_STRING, value)
    }

    @Test
    fun testGetBoolean_returnsDefaultValueWhenKeyNotFound() {
        whenever(preferences.getBoolean(TEST_KEY_GENERIC, TEST_DEF_BOOLEAN))
                .thenReturn(TEST_DEF_BOOLEAN)

        val value = prefsManager.getBoolean(TEST_KEY_GENERIC, TEST_DEF_BOOLEAN)

        assertEquals("Incorrect Boolean value", TEST_DEF_BOOLEAN, value)
    }

    @Test
    fun testGetInt_returnsDefaultValueWhenKeyNotFound() {
        whenever(preferences.getInt(TEST_KEY_GENERIC, TEST_DEF_INT))
                .thenReturn(TEST_DEF_INT)

        val value = prefsManager.getInt(TEST_KEY_GENERIC, TEST_DEF_INT)

        assertEquals("Incorrect Integer value", TEST_DEF_INT, value)
    }

    @Test
    fun testGetLong_returnsDefaultValueWhenKeyNotFound() {
        whenever(preferences.getLong(TEST_KEY_GENERIC, TEST_DEF_LONG))
                .thenReturn(TEST_DEF_LONG)

        val value = prefsManager.getLong(TEST_KEY_GENERIC, TEST_DEF_LONG)

        assertEquals("Incorrect Long value", TEST_DEF_LONG, value)
    }

    @Test
    fun testGetSerializedObject_returnsDeserializedValue() {
        val serializedObject = gson.toJson(TEST_OBJECT)
        whenever(preferences.getString(anyString(), anyString()))
                .thenReturn(serializedObject)
        val defObject = TestObject(TEST_STRING, TEST_INT)

        val actualObject = prefsManager.getSerializedObject(
                TEST_KEY_GENERIC, defObject, TEST_OBJECT.javaClass)

        assertEquals("Not default object", TEST_OBJECT, actualObject)
    }

    @Test
    fun testGetSerializedObject_returnsDefaultValueWhenKeyNotFound() {
        whenever(preferences.getString(TEST_KEY_GENERIC, ""))
                .thenReturn("")
        val defObject = "object"

        val actualObject = prefsManager.getSerializedObject(
                TEST_KEY_GENERIC, defObject, Any::class.java)

        assertEquals("Not default object", defObject, actualObject)
    }

    @Test
    fun clear_editorRemovesKeyFromPreferences() {
        prefsManager.clear(TEST_KEY_GENERIC)

        verify(editor).remove(TEST_KEY_GENERIC)
        verify(editor).apply()
    }

    data class TestObject(val string: String = "string", val integer: Int = 777)

    companion object {
        private const val TEST_KEY_GENERIC = "key.generic"
        private const val TEST_BOOLEAN = true
        private const val TEST_STRING = "test_string"
        private const val TEST_DEF_STRING = "test_default_string"
        private const val TEST_DEF_BOOLEAN = false
        private const val TEST_INT = 112
        private const val TEST_DEF_INT = 420
        private const val TEST_LONG = 360L
        private const val TEST_DEF_LONG = 666L
        private val TEST_OBJECT = TestObject()
    }
}