package com.telesoftas.core.storage.preferences

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class RxPreferencesStorageTest {
    private lateinit var rxStorage: RxPreferencesStorage<Any>
    private val storage = mock<PreferencesStorage<Any>>()
    private val item = Any()

    @Before
    fun setUp() {
        rxStorage = RxPreferencesStorage(storage)
    }

    @Test
    fun put_putsItemToStorageWithoutErrors() {
        rxStorage.put(item).test()

        verify(storage).put(item)
    }

    @Test
    fun put_noErrors_completesWithoutValues() {
        val observer = rxStorage.put(item).test()

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertNoValues()
    }

    @Test
    fun get_retrievesItemFromStorageWithoutErrors() {
        rxStorage.get().test()

        verify(storage).get()
    }

    @Test
    fun get_noErrors_returnsItemFromStorage() {
        given(storage.get()).willReturn(item)

        val observer = rxStorage.get().test()

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValue(item)
    }

    @Test
    fun clear_valueIsClearedFromStorageWithoutErrors() {
        val observer = rxStorage.clear().test()

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertNoValues()
    }
}