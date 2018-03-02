package com.telesoftas.core.storage.database.ormlite

import android.os.Build
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.telesoftas.core.storage.BuildConfig
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class DefaultDatabaseHelperTest {
    @Test(expected = IllegalArgumentException::class)
    fun onCreate_givenTableWithoutAnyDatabaseAnnotations_alwaysThrowsException() {
        val databaseHelper = getFailingDatabase()

        databaseHelper.onCreate(databaseHelper.writableDatabase)
    }

    @Test
    fun onCreate_givenTableWithDatabaseAnnotations_neverThrowsException() {
        val databaseHelper = getDefaultDatabase()

        databaseHelper.onCreate(databaseHelper.writableDatabase)
    }

    @Test
    fun onUpgrade_neverThrowsExceptions() {
        val databaseHelper = getDefaultDatabase()

        databaseHelper.onUpgrade(databaseHelper.writableDatabase, 1, 2)
    }

    @Test
    fun onDelete_neverThrowsException() {
        val databaseHelper = getDefaultDatabase()

        databaseHelper.onDelete(RuntimeEnvironment.application)
    }

    @Test
    fun execute_withReturnValue_alwaysCompletesWithoutErrors() {
        val databaseHelper = getDefaultDatabase()

        val observer = databaseHelper.execute<Int> { 2 + 2 }.test()

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValue(4)
    }

    @Test
    fun execute_withoutReturnValue_alwaysCompletesWithoutErrors() {
        val databaseHelper = getDefaultDatabase()

        val observer = databaseHelper.execute { Unit }.test()

        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun execute_withNullReturnValue_returnsNullEntityError() {
        val databaseHelper = getDefaultDatabase()

        val observer = databaseHelper.execute<Int> { null }.test()

        observer.assertError(DatabaseHelper.NullEntityException::class.java)
        observer.assertNotComplete()
    }

    private fun getFailingDatabase(): DefaultDatabaseHelper {
        return DefaultDatabaseHelper(RuntimeEnvironment.application,
                DatabaseConfiguration("test.db", 1),
                Schedulers.trampoline(),
                listOf(OrmLiteDatabaseTableModel(Int::class.java)))
    }

    private fun getDefaultDatabase(): DefaultDatabaseHelper {
        return DefaultDatabaseHelper(RuntimeEnvironment.application,
                DatabaseConfiguration("test.db", 1),
                Schedulers.trampoline(),
                listOf(OrmLiteDatabaseTableModel(TestEntity::class.java)))
    }

    @DatabaseTable
    data class TestEntity(@DatabaseField(id = true) val index: Int = 0)
}