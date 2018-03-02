package com.telesoftas.core.storage.database.ormlite

import android.os.Build
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.telesoftas.core.storage.BuildConfig
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class OrmLiteDatabaseStorageTest {
    private lateinit var databaseHelper: DefaultDatabaseHelper
    private lateinit var storageOrmLite: OrmLiteDatabaseStorage<TestEntity>

    @Before
    fun setUp() {
        databaseHelper = DefaultDatabaseHelper(RuntimeEnvironment.application,
                DatabaseConfiguration("test.db", 1),
                Schedulers.trampoline(),
                listOf(OrmLiteDatabaseTableModel(TestEntity::class.java)))
        storageOrmLite = OrmLiteDatabaseStorage(databaseHelper, TestEntity::class.java)
    }

    @Test
    fun putItems_sameItemsExist_storageDoesNotOverwriteItems() = runBlocking {
        val firstValues = listOf(TestEntity(1, "2"), TestEntity(2, "3"))
        val secondValues = listOf(TestEntity(1, "1"), TestEntity(2, "2"))
        storageOrmLite.putItems(firstValues)
        storageOrmLite.putItems(secondValues)

        val actual = storageOrmLite.getItems()

        assertEquals("Items were overwritten", firstValues, actual)
    }

    @Test
    fun putItems_storageAlwaysPutsItemsWithoutErrors() = runBlocking {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.putItems(testList)

        val actual = storageOrmLite.getItems()

        assertEquals("Items were not inserted", testList, actual)
    }

    @Test
    fun updateItem_storageAlwaysUpdatesItem() = runBlocking {
        val testId = 1
        val expected = TestEntity(1)
        storageOrmLite.updateItem(expected)

        val actual = storageOrmLite.getItemById(testId)

        assertEquals("Item was not inserted", expected, actual)
    }

    @Test
    fun updateItems_sameItemsExist_storageAlwaysOverwritesItems() = runBlocking {
        val firstValues = listOf(TestEntity(1, "2"), TestEntity(2, "3"))
        val secondValues = listOf(TestEntity(1, "1"), TestEntity(2, "2"))
        storageOrmLite.updateItems(firstValues)
        storageOrmLite.updateItems(secondValues)

        val actual = storageOrmLite.getItems()

        assertEquals("Items were not overwritten", secondValues, actual)
    }

    @Test
    fun updateItems_storageAlwaysUpdatesItems() = runBlocking {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.updateItems(testList)

        val actual = storageOrmLite.getItems()

        assertEquals("Items were not inserted", testList, actual)
    }

    @Test
    fun deleteAll_storageAlwaysDeletesAllItems() = runBlocking {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.putItems(testList)
        storageOrmLite.deleteAll()

        val actual = storageOrmLite.getItems()

        assertEquals("Items were not deleted", emptyList<TestEntity>(), actual)
    }

    @Test
    fun putItem_storageAlwaysPutsItem() = runBlocking {
        val testId = 1
        val expected = TestEntity(testId)
        storageOrmLite.putItem(expected)

        val actual = storageOrmLite.getItemById(testId)

        assertEquals("Item was not inserted", expected, actual)
    }

    @After
    fun tearDown() = runBlocking {
        storageOrmLite.deleteAll()
    }

    @DatabaseTable
    data class TestEntity(
            @DatabaseField(id = true) val id: Int = 0,
            @DatabaseField val value: String = ""
    )
}