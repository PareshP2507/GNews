package org.psquare.gnews.data.repository.refresh

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

class CategoryRefreshRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : CategoryRefreshRepository {

    private fun keyForCategory(category: String) = stringPreferencesKey("last_refresh_$category")

    override suspend fun saveLastRefresh(category: String, timestamp: Instant) {
        dataStore.edit {
            it[keyForCategory(category)] = timestamp.toString()
        }
    }

    override suspend fun getLastRefresh(category: String): Instant? =
        dataStore.data.first()[keyForCategory(category)]?.let { Instant.parse(it) }

    override fun getLastRefreshAsFlow(category: String): Flow<Instant?> =
        dataStore.data.map { prefs -> prefs[keyForCategory(category)]?.let { Instant.parse(it) } }
}