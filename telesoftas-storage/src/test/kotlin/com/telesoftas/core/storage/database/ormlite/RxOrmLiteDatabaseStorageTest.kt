package com.telesoftas.core.storage.database.ormlite

import android.os.Build
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.telesoftas.core.storage.BuildConfig
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [(Build.VERSION_CODES.LOLLIPOP)])
class RxOrmLiteDatabaseStorageTest {
    private lateinit var databaseHelper: DefaultDatabaseHelper
    private lateinit var storageOrmLite: RxOrmLiteDatabaseStorage<TestEntity>

    @Before
    fun setUp() {
        databaseHelper = DefaultDatabaseHelper(RuntimeEnvironment.application,
                DatabaseConfiguration("test.db", 1),
                Schedulers.trampoline(),
                listOf(OrmLiteDatabaseTableModel(TestEntity::class.java)))
        storageOrmLite = RxOrmLiteDatabaseStorage(databaseHelper, TestEntity::class.java)
    }

    @Test
    fun putItems_sameItemsExist_storageDoesNotOverwriteItems() {
        val firstValues = listOf(TestEntity(1, "2"), TestEntity(2, "3"))
        val secondValues = listOf(TestEntity(1, "1"), TestEntity(2, "2"))
        storageOrmLite.putItems(firstValues).test().assertComplete()
        storageOrmLite.putItems(secondValues).test().assertComplete()

        val observer = storageOrmLite.getItems().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(firstValues)
    }

    @Test
    fun putItems_storageAlwaysPutsItemsWithoutErrors() {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.putItems(testList).test()

        val observer = storageOrmLite.getItems().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(testList)
    }

    @Test
    fun updateItem_storageAlwaysUpdatesItem() {
        val testId = 1
        val testItem = TestEntity(1)
        storageOrmLite.updateItem(testItem).test()

        val observer = storageOrmLite.getItemById(testId).test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(testItem)
    }

    @Test
    fun updateItems_sameItemsExist_storageAlwaysOverwritesItems() {
        val firstValues = listOf(TestEntity(1, "2"), TestEntity(2, "3"))
        val secondValues = listOf(TestEntity(1, "1"), TestEntity(2, "2"))
        storageOrmLite.updateItems(firstValues).test().assertComplete()
        storageOrmLite.updateItems(secondValues).test().assertComplete()

        val observer = storageOrmLite.getItems().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(secondValues)
    }

    @Test
    fun updateItems_storageAlwaysUpdatesItems() {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.updateItems(testList).test()

        val observer = storageOrmLite.getItems().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(testList)
    }

    @Test
    fun deleteAll_storageAlwaysDeletesAllItems() {
        val testList = listOf(TestEntity(1), TestEntity(2))
        storageOrmLite.putItems(testList).test()
        storageOrmLite.deleteAll().test()

        val observer = storageOrmLite.getItems().test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(emptyList())
    }

    @Test
    fun putItem_storageAlwaysPutsItem() {
        val testId = 1
        val testItem = TestEntity(testId)
        storageOrmLite.putItem(testItem).test()

        val observer = storageOrmLite.getItemById(testId).test()

        observer.assertNoErrors()
        observer.assertComplete()
        observer.assertValue(testItem)
    }

    @After
    fun tearDown() {
        storageOrmLite.deleteAll().blockingAwait()
    }

    @DatabaseTable
    data class TestEntity(
            @DatabaseField(id = true) val id: Int = 0,
            @DatabaseField val value: String = ""
    )
}