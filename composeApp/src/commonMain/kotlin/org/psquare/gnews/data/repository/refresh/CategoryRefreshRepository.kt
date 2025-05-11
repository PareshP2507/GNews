package org.psquare.gnews.data.repository.refresh

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface CategoryRefreshRepository {

    suspend fun saveLastRefresh(category: String, timestamp: Instant)

    suspend fun getLastRefresh(category: String): Instant?

    fun getLastRefreshAsFlow(category: String): Flow<Instant?>
}