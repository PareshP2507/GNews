package org.psquare.gnews.init

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.ktor.utils.io.InternalAPI
import io.ktor.utils.io.locks.SynchronizedObject
import io.ktor.utils.io.locks.synchronized
import okio.Path.Companion.toPath

private lateinit var dataStore: DataStore<Preferences>

@OptIn(InternalAPI::class)
private val lock = SynchronizedObject()

/**
 * Creates a [DataStore] instance.
 */
@OptIn(InternalAPI::class)
fun createDataStore(createPath: () -> String): DataStore<Preferences> =
    synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory.createWithPath(produceFile = { createPath().toPath() })
                .also { dataStore = it }
        }
    }