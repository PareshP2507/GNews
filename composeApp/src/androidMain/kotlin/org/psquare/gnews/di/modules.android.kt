package org.psquare.gnews.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.psquare.gnews.data.db.DB_FILE_NAME
import org.psquare.gnews.data.db.GNewsDatabase
import org.psquare.gnews.init.createDataStore

internal actual val platformDbModule: Module
    get() = module {
        single { createGNewsDatabase(androidContext()) }
    }

private fun createGNewsDatabase(context: Context): GNewsDatabase =
    Room.databaseBuilder<GNewsDatabase>(context = context, name = DB_FILE_NAME)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

internal actual val platformDataStoreModule: Module
    get() = module {
        single { createDataStore { createDataStoreFilePath(androidContext()) } }
    }

private fun createDataStoreFilePath(context: Context) =
    context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath