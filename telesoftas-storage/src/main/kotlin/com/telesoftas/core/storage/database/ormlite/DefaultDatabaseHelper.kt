package com.telesoftas.core.storage.database.ormlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.misc.TransactionManager
import com.j256.ormlite.support.ConnectionSource
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import timber.log.Timber
import java.sql.SQLException

class DefaultDatabaseHelper(
        context: Context,
        databaseConfig: DatabaseConfiguration,
        private val singleThreadScheduler: Scheduler,
        private val databaseTableModels: List<DatabaseTableModel>
) : OrmLiteSqliteOpenHelper(
        context,
        databaseConfig.databaseName,
        databaseConfig.cursorFactory,
        databaseConfig.databaseVersion
), DatabaseHelper {
    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            initializeDatabase(connectionSource)
        } catch (cause: SQLException) {
            Timber.e(cause, "Database error while creating table")
        }
    }

    override fun onUpgrade(
            database: SQLiteDatabase,
            connectionSource: ConnectionSource,
            oldVersion: Int,
            newVersion: Int
    ) {
        performDestructiveMigration()
    }

    private fun performDestructiveMigration() {
        databaseTableModels.forEach { initializer -> initializer.dropTable(connectionSource) }
        initializeDatabase(connectionSource)
    }

    private fun initializeDatabase(connectionSource: ConnectionSource) {
        databaseTableModels.forEach { initializer -> initializer.createTable(connectionSource) }
    }

    @Throws(SQLException::class)
    fun onDelete(context: Context) {
        context.deleteDatabase(context.getDatabasePath(databaseName).absolutePath)
    }

    override fun <T> execute(action: () -> T?): Single<T> {
        val single = Single.create<T> { emitter ->
            val result = TransactionManager.callInTransaction(getConnectionSource(), action)
            if (result == null) {
                emitter.onError(DatabaseHelper.NullEntityException())
            } else {
                emitter.onSuccess(result)
            }
        }
        return single.subscribeOn(singleThreadScheduler)
    }

    override fun execute(action: () -> Unit): Completable {
        val completable = Completable.fromAction {
            TransactionManager.callInTransaction(getConnectionSource(), action)
        }
        return completable.subscribeOn(singleThreadScheduler)
    }
}
