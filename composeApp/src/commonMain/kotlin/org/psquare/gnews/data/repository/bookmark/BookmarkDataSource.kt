package org.psquare.gnews.data.repository.bookmark

import kotlinx.coroutines.flow.Flow
import org.psquare.gnews.data.entities.DbArticleEntity

interface BookmarkDataSource {

    interface Local {
        fun getBookmarks(): Flow<List<DbArticleEntity>>
    }
}