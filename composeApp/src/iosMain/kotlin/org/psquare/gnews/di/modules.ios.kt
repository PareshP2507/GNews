package org.psquare.gnews.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import org.psquare.gnews.data.db.DB_FILE_NAME
import org.psquare.gnews.data.db.GNewsDatabase
import org.psquare.gnews.init.createDataStore
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal actual val platformDbModule: Module
    get() = module {
        single { createGNewsDatabase() }
    }

private fun createGNewsDatabase(): GNewsDatabase {
    val dbFile = "${fileDirectory()}/$DB_FILE_NAME"
    return Room.databaseBuilder<GNewsDatabase>(name = dbFile)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}

@OptIn(ExperimentalForeignApi::class)
fun getDataStoreFilePath(): String {
    val documentDir: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDir).path + "/$DATASTORE_FILE_NAME"
}

internal actual val platformDataStoreModule: Module
    get() = module {
        single { createDataStore { getDataStoreFilePath() } }
    }